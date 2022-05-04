package moa.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProjectDao {

	//프로젝트 번호를 넣으면 해당 프로젝트 한개 조회
	public ProjectDto selectOne(int ProjectNo) throws Exception {
		String sql = "select * from project where project_no = ?";
		
		Connection con = JdbcUtils.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, ProjectNo);
		
		ResultSet rs = ps.executeQuery();
		
		
		ProjectDto projectDto;
		
		if(rs.next()) {
			projectDto = new ProjectDto();
			projectDto.setProjectNo(rs.getInt("project_no"));
			projectDto.setProjectSellerNo(rs.getInt("project_seller_no"));
			projectDto.setProjectName(rs.getString("project_name"));
			projectDto.setProjectSummary(rs.getString("project_summary"));
			projectDto.setProjectTargetMoney(rs.getInt("project_target_money"));
			projectDto.setProjectPresentMoney(rs.getInt("project_present_money"));
			projectDto.setProjectSponsorNo(rs.getInt("project_sponsor_no"));
			projectDto.setProjectStartDate(rs.getDate("project_start_date"));
			projectDto.setProjectSemiFinish(rs.getDate("project_semi_finish"));
			projectDto.setProjectFinishDate(rs.getDate("project_finish_date"));
			projectDto.setProjectPermission(rs.getString("project_permission"));
			projectDto.setProjectCategory(rs.getString("project_category"));
		}else {
			projectDto = null;
		}
		
		con.close();
		
		return projectDto;
		
	}
	
	//프로젝트 번호를 넣으면 프로젝트 번호, 남은일수, 달성률, 좋아요 수 포함된 Vo반환
	public ProjectVo selectVo(int ProjectNo) throws Exception {
		String sql = "select "
				+ "project_no, "
				+ "(project_semi_finish - trunc(sysdate) + 1) daycount, "
				+ "trunc(project_present_money/project_target_money*100, 1) percent, "
				+ "(select count(j.member_no) from project p left outer join joa j on p.project_no = j.project_no where p.project_no=?) joacount "
				+ "from project where project_no = ?";
		
		Connection con = JdbcUtils.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setInt(1, ProjectNo);
		ps.setInt(2, ProjectNo);
		
		ResultSet rs = ps.executeQuery();
		
		ProjectVo projectVo;
		if(rs.next()) {
			projectVo = new ProjectVo();
			projectVo.setProjectNo(rs.getInt("project_no"));
			projectVo.setDaycount(rs.getInt("daycount"));
			projectVo.setPercent(rs.getDouble("percent"));
			projectVo.setJoacount(rs.getInt("joacount"));
		}else {
			projectVo = null;
		}
		
		con.close();
		
		return projectVo;
	}
	//프로젝트 생성(신청)
	public void insert(ProjectDto projectDto) throws Exception {
		Connection con = JdbcUtils.getConnection();
		
		String sql = "insert into project(project_no, project_seller_no, project_category, project_name, project_summary, project_target_money, project_start_date, project_semi_finish, project_finish_date)"
					+ " values(project_seq.nextval,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, 23);
		ps.setString(2, projectDto.getProjectCategory());
		ps.setString(3, projectDto.getProjectName());
		ps.setString(4, projectDto.getProjectSummary());
		ps.setInt(5, projectDto.getProjectTargetMoney());
		ps.setDate(6, projectDto.getProjectStartDate());
		ps.setDate(7, projectDto.getProjectSemiFinish());
		ps.setDate(8, projectDto.getProjectFinishDate());
		ps.execute();
		
		con.close();
		
	}
}
