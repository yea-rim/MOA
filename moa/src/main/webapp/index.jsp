<%@page import="moa.beans.SellerDto"%>
<%@page import="moa.beans.SellerDao"%>
<%@page import="moa.beans.ProjectVo"%>
<%@page import="moa.beans.ProjectDto"%>
<%@page import="java.util.List"%>
<%@page import="moa.beans.ProjectDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	ProjectDao projectDao = new ProjectDao();
	List<ProjectDto> list1 = projectDao.selectTop();
	
	List<ProjectDto> list2 = projectDao.selectNew();
%>

<%
	ProjectVo projectVo;
	SellerDao sellerDao = new SellerDao();
	SellerDto sellerDto;
%>
<style>
.flex-container1 {
	display: flex;
	flex-direction: row;
	flex-wrap: wrap;
}
.flex-container2 {
	display: flex;
	flex-direction: column;
	flex-wrap: wrap;
	justify-content: center;
}
.flex-items1 {
	flex-basis: 20%;
	padding: 10px;
}

.flex-items2 {
	flex-basis: 50%;
	padding: 10px;
}

.flex-items-a{
	flex-basis: 5%;
	display: flex;
	flex-direction: column;
	justify-content: center;
}
.flex-items-b{
	flex-basis: 75%;
}
.flex-items-c{
	flex-basis: 20%;
}
.project-name {
	font-size: 25px;
	padding: 10px;
	overflow:hidden;
	text-overflow:ellipsis;
	white-space:wrap;
}
.new-name {
	font-size: 20px;
	padding: 5px;
	overflow:hidden;
	text-overflow:ellipsis;
	white-space:wrap;
}
.percent{
	color: #B899CD;
	font-size: 15px;
	padding: 10px;
}
.seller{
	font-size: 15px;
	padding: 0px 10px;
}
</style>
<jsp:include page="/template/header.jsp"></jsp:include>

				<div class="row fill">
                    <img src="https://dummyimage.com/1305x250" alt="" class="fill">
                </div>
                
				<%-- 인기프로젝트 목록 --%>
				<div class="row flex-container1">
	                <div class="row left big-text mt50 mlr10 flex-items2">
    	                <a href="<%=request.getContextPath()%>/project/ongoingList.jsp?sort=인기순" class="link">인기 프로젝트</a>
    	                <hr style="border: solid lightgray 0.5px" />
    	                <% 
    	                	int count = 0;
    	                %>
    	                <div class="row flex-container2">
    	                <%for(ProjectDto projectDto : list1){ %>
    	                <% count++; %>
    	                <div class="container fill" style="border-bottom:0.5px solid black">
    	               		<div class="row flex-container1">
    	               		<div class="row flex-items-a">
    	               			<span style="color:#B899CD"><%=count %></span>
    	               		</div>
    	                	<div class="row flex-container2 flex-items-b">
	    	                	<div class="row project-name m10">
	    	                		<a href="<%=request.getContextPath() %>/project/projectDetail.jsp?projectNo=<%=projectDto.getProjectNo() %>" class="link">
	    	                			<%=projectDto.getProjectName() %>
	    	                		</a>
	    	                	</div>
	    	                	
	    	                	<%
	    	                		projectVo = projectDao.selectVo(projectDto.getProjectNo()); 
	    	                  		sellerDto = sellerDao.selectOne(projectDto.getProjectSellerNo());
	    	                	%>
	    	                	<div class="row seller"><%=sellerDto.getSellerNick() %></div>
	    	                	<%-- <div class="row percent"><%=projectVo.getPercent() %> % </div> --%>
	    	                </div>
    	                		
    	                		<div class="row flex-items-c m10">
    	                			<a href="<%=request.getContextPath() %>/project/projectDetail.jsp?projectNo=<%=projectDto.getProjectNo() %>">
	    	                			<img src="https://dummyimage.com/500x400" width="110px" height="110px">
	    	                		</a>
	    	                	</div>
	    	                </div> 
	    	               </div>
    	                <%} %>
    	                
    	                </div>
        	        </div>
        	        
        	        <%-- 공지사항 --%>
	                <div class="row left big-text mt50 mlr10 flex-items1">
    	                <a href="<%=request.getContextPath() %>/notice/list.jsp" class="link">공지사항</a>
    	                 	                <hr style="border: solid lightgray 0.5px" />
    	                <% 
    	                	count = 0;
    	                %>
    	                <div class="row flex-container2">
    	                <%for(ProjectDto projectDto : list1){ %>
    	                <% count++; %>
    	                <div class="container fill" style="border-bottom:0.5px solid black">
    	               		<div class="row flex-container1">
    	               		<div class="row flex-items-a">
    	               			<span style="color:#B899CD"><%=count %></span>
    	               		</div>
    	                	<div class="row flex-container2 flex-items-b">
	    	                	<div class="row project-name m10">
	    	                		<a href="<%=request.getContextPath() %>/project/projectDetail.jsp?projectNo=<%=projectDto.getProjectNo() %>" class="link">
	    	                			<%=projectDto.getProjectName() %>
	    	                		</a>
	    	                	</div>
	    	                	
	    	                	<%
	    	                		/* ProjectVo projectVo = projectDao.selectVo(projectDto.getProjectNo()); */ 
	    	                		sellerDao = new SellerDao();
	    	                  		sellerDto = sellerDao.selectOne(projectDto.getProjectSellerNo());
	    	                	%>
	    	                	<div class="row seller"><%=sellerDto.getSellerNick() %></div>
	    	                	<%-- <div class="row percent"><%=projectVo.getPercent() %> % </div> --%>
	    	                </div>
    	                		
    	                		<div class="row flex-items-c m10">
    	                			<a href="<%=request.getContextPath() %>/project/projectDetail.jsp?projectNo=<%=projectDto.getProjectNo() %>">
	    	                			<img src="https://dummyimage.com/500x400" width="110px" height="110px">
	    	                		</a>
	    	                	</div>
	    	                </div> 
	    	               </div>
    	                <%} %>
    	                
    	                </div>
        	        </div>
				</div>
				
				<%-- 신규프로젝트 목록 --%>
				 <hr style="border: solid lightgray 0.5px" />
                <div class="row left big-text mt50 mlr10">
                    <a href="<%=request.getContextPath()%>/project/ongoingList.jsp" class="link">신규 프로젝트</a>
                </div>
                
				<div class="row flex-container1">
				<%for(ProjectDto projectDto : list2){ %>
					<div class="row flex-items1 m10">
						<div class="row">
							<a href="<%=request.getContextPath() %>/project/projectDetail.jsp?projectNo=<%=projectDto.getProjectNo() %>"><img src="https://dummyimage.com/200x200" width="240px" height="240px"></a>
						</div>
						<div class="row left m10 new-name">
							<a href="<%=request.getContextPath() %>/project/projectDetail.jsp?projectNo=<%=projectDto.getProjectNo() %>" class="link">
								<span><%=projectDto.getProjectName() %></span>
							</a>
						</div>
					</div>
				<%} %>
				</div>
				
<jsp:include page="/template/footer.jsp"></jsp:include>