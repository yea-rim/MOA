package moa.servlet.project;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import moa.beans.AttachDao;
import moa.beans.AttachDto;
import moa.beans.ProjectAttachDao;
import moa.beans.ProjectAttachDto;
import moa.beans.ProjectDao;
import moa.beans.ProjectDto;
import moa.beans.RewardDao;
import moa.beans.RewardDto;


@WebServlet(urlPatterns = "/project/insert.do")
public class ProjectInsertServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {	
			
			//파일 저장
			String path = System.getProperty("user.home")+"/upload";//저장할 경로 /운영체제에서 사용자에게 제공되는 home폴더
			System.out.println(path);

			File dir = new File(path);
			dir.mkdirs(); //폴더생성
			
			int max = 1*1024*1024; //최대 크기 제한(byte);
			String encoding = "UTF-8";
			
			DefaultFileRenamePolicy policy = new DefaultFileRenamePolicy();
			MultipartRequest mRequest = new MultipartRequest(req, path, max, encoding, policy);
			//정상실행이 되었다면 모든 정보는 mRequest 객체에 들어있다.
			//-들어있는 정보를 꺼내서 화면에 출력하거나 DB에 저장하거나 원하는 작업을 수행한다.
			
			
			//프로필사진 정보
		 	String profileuploadName = mRequest.getOriginalFileName("profileAttach");
		 	String profilesaveName = mRequest.getFilesystemName("profileAttach");
		 	String profilecontentType = mRequest.getContentType("profileAttach");
		 	File profiletarget = mRequest.getFile("profileAttach");
		 	int profilefileSize = 0;
		 	if(profiletarget != null){
		 		profilefileSize = (int)profiletarget.length();
		 	}
			
		 	
		 	//상세사진 정보
		 	String detailuploadName = mRequest.getOriginalFileName("detailAttach");
		 	String detailsaveName = mRequest.getFilesystemName("detailAttach");
		 	String detailcontentType = mRequest.getContentType("detailAttach");
		 	File detailtarget = mRequest.getFile("detailAttach");
		 	int detailfileSize = 0;
		 	if(detailtarget != null){
		 		detailfileSize = (int)detailtarget.length();
		 	}
		
			
			//프로젝트 신청 시퀀스번호 생성
			ProjectDao projectDao = new ProjectDao();
			int projectNo = projectDao.getSequence();
			
			//프로젝트 신청 처리
			ProjectDto projectDto = new ProjectDto();
			projectDto.setProjectNo(projectNo); //가져온 시퀀스 번호 넣어주기
			projectDto.setProjectCategory(mRequest.getParameter("projectCategory"));
			projectDto.setProjectName(mRequest.getParameter("projectName"));
			projectDto.setProjectSummary(mRequest.getParameter("projectSummary"));
			projectDto.setProjectTargetMoney(Integer.parseInt(mRequest.getParameter("projectTargetMoney")));
			projectDto.setProjectStartDate(Date.valueOf(mRequest.getParameter("projectStartDate")));
			projectDto.setProjectSemiFinish(Date.valueOf(mRequest.getParameter("projectSemiFinish")));
			projectDto.setProjectFinishDate(Date.valueOf(mRequest.getParameter("projectFinishDate")));
			
			//projectDao.insert(projectDto); 
		
			
			//프로필 파일 정보 저장											
			AttachDao attachDao = new AttachDao();
			if(mRequest.getFile("profileAttach") != null) {		
				AttachDto attachDto = new AttachDto();
				attachDto.setAttachNo(attachDao.getSequence());
				attachDto.setAttachUploadname(profileuploadName);
				attachDto.setAttachSavename(profilesaveName);
				attachDto.setAttachType(profilecontentType);
				attachDto.setAttachSize(profilefileSize);
				
				//attachDao.insert(attachDto);
				
				ProjectAttachDto projectAttachDto = new ProjectAttachDto();
				projectAttachDto.setProjectNo(projectNo);
				projectAttachDto.setAttachNo(attachDto.getAttachNo());
				projectAttachDto.setAttachType("프로필");
			
				
				ProjectAttachDao projectAttachDao = new ProjectAttachDao();
				//projectAttachDao.insert(projectAttachDto);			
			}		
			
			//상세사진 저장
			if(mRequest.getFile("detailAttach") != null) {			
				AttachDto attachDto = new AttachDto();
				attachDto.setAttachNo(attachDao.getSequence());
				attachDto.setAttachUploadname(detailuploadName);
				attachDto.setAttachSavename(detailsaveName);
				attachDto.setAttachType(detailcontentType);
				attachDto.setAttachSize(detailfileSize);
				
				//attachDao.insert(attachDto);
				
				ProjectAttachDto projectAttachDto = new ProjectAttachDto();
				projectAttachDto.setProjectNo(projectNo);
				projectAttachDto.setAttachNo(attachDto.getAttachNo());
				projectAttachDto.setAttachType("본문");
			
				
				ProjectAttachDao projectAttachDao = new ProjectAttachDao();
				//projectAttachDao.insert(projectAttachDto);			
			}		
			
			//리워드 신청 처리
			String [] rewardName = mRequest.getParameterValues("rewardName"); 
			String [] rewardContent = mRequest.getParameterValues("rewardContent"); 
			String [] rewardPrice = mRequest.getParameterValues("rewardPrice"); 
			String [] rewardStock = mRequest.getParameterValues("rewardStock"); 	
			 
			 RewardDao rewardDao = new RewardDao();
			for(int i=0; i<rewardName.length; i++) {
				RewardDto rewardDto = new RewardDto();
				
				rewardDto.setRewardProjectNo(projectNo);
				rewardDto.setRewardName(rewardName[i]);
				rewardDto.setRewardContent(rewardContent[i]);
				rewardDto.setRewardPrice(Integer.parseInt(rewardPrice[i]));
				rewardDto.setRewardStock(Integer.parseInt(rewardStock[i]));
				
				//rewardDao.insert(rewardDto);
			}
			
			
			System.out.println("성공");

			resp.sendRedirect("insert_success.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(500);
		}
	}
}