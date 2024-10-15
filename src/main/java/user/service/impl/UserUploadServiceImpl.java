package user.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import user.bean.UserUploadDTO;
import user.dao.UserUploadDAO;
import user.service.ObjectStorageService;
import user.service.UserUploadService;

@Service
public class UserUploadServiceImpl implements UserUploadService {
	@Autowired
	private UserUploadDAO userUploadDAO;
	@Autowired
	private HttpSession session;
	@Autowired
	private ObjectStorageService objectStorageService;
	
	private String bucketName = "bitcamp-9th-bucket-131";

	@Override
	public void upload(List<UserUploadDTO> imageUploadList) {
		userUploadDAO.upload(imageUploadList);
	}

	@Override
	public List<UserUploadDTO> uploadList() {
		return userUploadDAO.uploadList();
	}

	@Override
	public UserUploadDTO getUploadDTO(String seq) {
		// TODO Auto-generated method stub
		return userUploadDAO.getUploadDTO(seq);
	}

	@Override
	public void uploadUpdate(UserUploadDTO userUploadDTO, MultipartFile img) {
		//실제폴더
		String filePath = session.getServletContext().getRealPath("WEB-INF/storage");
		System.out.println("실제 폴더 = " + filePath);
		
		UserUploadDTO dto = userUploadDAO.getUploadDTO(userUploadDTO.getSeq()+"");
		String imageFileName;
		
		if(img.getSize() != 0) {
			//Object Storage(NCP)는 이미지를 덮어쓰지 않는다.
			//DB에서 seq에 해당하는 imageFileNaem을 꺼내와서 Object Storage(NCP)의 이미지를 삭제 후 새로운 이미지를 올린다.
			
			imageFileName = dto.getImageFileName();
			System.out.println("이미지 파일 이름입니다 : " + imageFileName);
			
			//Object Storage(NCP) 이미지 삭제
			objectStorageService.deleteFile(bucketName, "storage/", imageFileName);
			
			//Object Storage(NCP) 새로운 이미지 등록
			imageFileName = objectStorageService.uploadFile(bucketName, "storage/", img);
			
			String imageOriginalFileName = img.getOriginalFilename();
			File file = new File(filePath, imageOriginalFileName);
			
			try {
				img.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			userUploadDTO.setImageFileName(imageFileName);
			userUploadDTO.setImageOriginalFileName(imageOriginalFileName);
		} else {
			userUploadDTO.setImageFileName(dto.getImageFileName());
			userUploadDTO.setImageOriginalFileName(dto.getImageOriginalFileName());
		}
		
		//DB
		 userUploadDAO.uploadUpdate(userUploadDTO);

	}

	@Override
	public void uploadDelet(String[] check) {
		
		//데이터를 List에 담아야한다.
		List<String> list = new ArrayList<>();
		
		//Object Storage에 있는 이미지도 삭제 => imageFileName를 list에 담는다.
		for(String seq : check) {
			String imageFileName = userUploadDAO.getImageFileName(Integer.parseInt(seq));
			list.add(imageFileName);
		}
		objectStorageService.deleteFile(bucketName, "storage/", list);
		
		//db도 삭제
		userUploadDAO.uploadDelet(list);
	}

		
	}















