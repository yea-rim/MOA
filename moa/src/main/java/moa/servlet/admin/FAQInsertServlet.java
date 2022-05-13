package moa.servlet.admin;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import moa.beans.AttachDao;
import moa.beans.AttachDto;
import moa.beans.CommunityDao;
import moa.beans.CommunityDto;
import moa.beans.CommunityPhotoDao;
import moa.beans.CommunityPhotoDto;
import moa.beans.MoaFaqDao;
import moa.beans.MoaFaqDto;

@WebServlet(urlPatterns = "/admin/insert.do")
public class FAQInsertServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {

			// 시퀀스 생성
			MoaFaqDao moaFaqDao = new MoaFaqDao();
			int faqNo = moaFaqDao.getFaqNo();

			MoaFaqDto moaFaqDto = new MoaFaqDto();
			moaFaqDto.setFaqNo(faqNo);
			moaFaqDto.setFaqCategory(req.getParameter("faqCategory"));
			moaFaqDto.setFaqTitle(req.getParameter("faqTitle"));
			String faqContent = req.getParameter("faqContent");
			faqContent = faqContent.replace("\r\n", "<br>");
			moaFaqDto.setFaqContent(faqContent);

			// 작성
			moaFaqDao.insert(moaFaqDto);

			// 출력
			resp.sendRedirect("faq_detail.jsp?faqNo=" + moaFaqDto.getFaqNo());

		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(500);
		}
	}
}
