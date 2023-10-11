<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" href="/resources/css/reset.css">
      <link rel="stylesheet" href="/resources/css/sub.css">
      <link rel="stylesheet" href="/resources/css/header.css">
      <link rel="stylesheet" href="/resources/css/footer.css">
      <link rel="stylesheet" href="/resources/css/notice/notice.css">
      <title>lots of dots</title>
   </head>
   <body>
      <div class="container">
         <jsp:include page="/WEB-INF/views/include/clickUp.jsp"></jsp:include>

         <!-- header -->
         <header>
            <jsp:include page="/WEB-INF/views/include/header_logo.jsp"></jsp:include>
            <nav class="header_nav">
               <ul>
                  <li>
                     <a href="/category/about/about.jsp"><img src="/resources/images/Icons/circle_icon.png" alt="About" class="icons"></a>
                  </li>
                  <li>
                     <a href="/category/project/project.jsp"><img src="/resources/images/Icons/circle_icon.png" alt="Project" class="icons"></a>
                  </li>
                  <li id="dotsLogo">
                     <a href="/category/dots/dots.jsp"><img src="/resources/images/Icons/loader_loading_icon.png" alt="Dots" class="icons"></a>
                  </li>
                  <li>
                     <a href="/category/archive/archive.jsp"><img src="/resources/images/Icons/circle_icon.png" alt="Archive" class="icons"></a>
                  </li>
                  <li id="blogLogo">
                     <a href="/category/blog/blog.jsp"><img src="/resources/images/Icons/circle_icon.png" alt="Blog" class="icons"></a>
                  </li>
               </ul>
            </nav>
            <jsp:include page="/WEB-INF/views/include/header_rightNav.jsp"></jsp:include>
         </header>

         <!-- main -->
         <main class="dots_NoticeMain">
            <section class="dots_NoticeBox">
               <section class="dots_NoticeTitle">
                  <a href="/notice/list.do?currentPage=1">Notice</a>
               </section>
               <section class="dots_NoticeTableBox">
                  <table class="dots_NoticeTable">
                     <tr>
                        <th>Subject</th>
                        <th>Date</th>
                     </tr>
					<c:forEach var="notice" items="${ sList }">
					<tr>
						<td><a href="/notice/detail.do?noticeNo=${ notice.noticeNo }">${ notice.noticeSubject }</a></td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${ notice.nUpdateDate }"/></td>
					</tr>
					</c:forEach>
                  </table>
               </section>
               <div class="dots_NoticeList">
                  <div class="dots_insertButton">
                     <a href="/notice/insert.do"><input type="button" value="    글쓰기    "></a>
                  </div>
                  <div class="dots_searchForm">
                     <form action="/notice/search.do" method="get">
                        <select name="searchCondition">
                           <option value="all"		<c:if test="${ searchCondition == 'all'}">selected</c:if>>전체</option>
						   <option value="writer" 	<c:if test="${ searchCondition == 'writer'}">selected</c:if>>작성자</option>
						   <option value="title" 	<c:if test="${ searchCondition == 'title'}">selected</c:if>>제목</option>
						   <option value="content" 	<c:if test="${ searchCondition == 'content'}">selected</c:if>>내용</option>
                        </select>
                        <input type="text" name="searchKeyword" placeholder="검색어를 입력하세요." value="${ searchKeyword }">
                        <input type="submit" value="검색">
                     </form>
                  </div>
               </div>

               <section class="dots_Noticebutton">
               		<c:if test="">
               		
               		</c:if>
					${ navi }
               </section>
            </section>
         </main>

         <!-- footer -->
         <jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
      </div>
      <script>
         document.addEventListener("DOMContentLoaded", () => {
            document.querySelector("#shopping_bag_icon").addEventListener("click", () => {
                  document.querySelector("#shopping_bag").style.display = "block";
            })
            document.querySelector("#shopping_bag_exit_icon").addEventListener("click", () => {
                  document.querySelector("#shopping_bag").style.display = "none";
            })
         });
      </script>
   </body>
</html>