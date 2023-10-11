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
      <link rel="stylesheet" href="/resources/css/notice/notice-detail.css">
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
                  <div class="dots_NoticeSubTitle">
                     ${ notice.noticeSubject}
                  </div>
                  <div class="dots_NoticeSubWrite">
                     <p>
                        ${ notice.noticeContent}
                     </p>
                  </div>
                  <div class="dots_NoticeSubDate">
                     <c:if test="${ !empty notice.noticeFileName }">
                     	<a href="../resources/nuploadFiles/${ notice.noticeFileRename }" download>${ notice.noticeFileName } /&nbsp;</a>
                     </c:if>
                     <fmt:formatDate pattern="yyyy-MM-dd" value="${ notice.nUpdateDate }"/>
                  </div>
               </section>
               
               <section>
				<!-- 댓글 목록 -->
				<table class="dots_ReplyList">
					<c:forEach var="reply" items="${ rList }">
						<tr>
							<td>${ reply.replyWriter }</td>
							<td>${ reply.replyContent }</td>
							<td>${ reply.rCreateDate }</td>
							<td>
								<c:if test="${ reply.replyWriter eq memberId }">
									<a href="javascript:void(0);" onclick="showUpdateForm(this, '${ reply.replyContent }');">수정</a>
									<c:url var="replyDeleteUrl" value="/reply/delete.do">
										<c:param name="replyNo" 	value="${ reply.replyNo }"></c:param>
										<c:param name="replyWriter" value="${ reply.replyWriter }"></c:param>	<!-- 본인 것만 지우게 하기 위해 작성자 추가 -->
										<c:param name="refNoticeNo" value="${ reply.refNoticeNo }"></c:param>		<!-- 성공하면 detail로 가기위한 noticeNo 세팅 -->
									</c:url>
									<a href="javascript:void(0);" onclick="deleteReply('${ replyDeleteUrl }');">삭제</a>
								</c:if>
							</td>
						</tr>
						<tr style="display: none;">
							<form action="/reply/update.do" method="post">
								<input type="hidden" name="replyNo" value="${ reply.replyNo }">
								<input type="hidden" name="refNoticeNo" value="${ reply.refNoticeNo }">
								<td><input type="submit" value="    Reply    "></td>
								<td colspan="3"><input type="text" name="replyContent" value="${ reply.replyContent }"></td>
							</form>
						</tr>
					</c:forEach>
				</table>
				
				<!-- 댓글 등록 -->
				<form action="/reply/insert.do" method="post" class="dots_replyInsertForm">
					<input type="hidden" name="refNoticeNo" value="${ notice.noticeNo }">
					<table>
						<tr>
							<td>
								<input type="text" name="replyContent">
							</td>
							<td>
								<input type="submit" value="    Reply    ">
							</td>
						</tr>
					</table>
				</form>
               </section>
				
               <div class="dots_NoticeList">
                  <a href="javascript:void(0)" onclick="javascript:history.go(-1);"><input type="button" value="    List    "></a>
               </div>
				<c:url var="updateUrl" value="/notice/modify.do">
					<c:param name="noticeNo" value="${ notice.noticeNo }"></c:param>
					<c:param name="noticeWriter" value="${ notice.noticeWriter }"></c:param>
				</c:url>
				<c:url var="deleteUrl" value="/notice/delete.do">
					<c:param name="noticeNo" value="${ notice.noticeNo }"></c:param>
					<c:param name="noticeWriter" value="${ notice.noticeWriter }"></c:param>
				</c:url>
				<c:if test="${ notice.noticeWriter eq memberId }">
	               <div class="dots_NoticeList">
	                  <a href="javascript:void(0)" onclick="updateCheck('${ updateUrl }');"><input type="button" value="    Update    "></a><br>
	               </div>
	               <div class="dots_NoticeList">
	                  <a href="javascript:void(0)" onclick="deleteCheck('${ deleteUrl }');"><input type="button" value="    Delete    "></a><br>
	               </div>
				</c:if>
               <section class="dots_Noticebutton">
                  <input type="button" value="<">
<!--                   <input type="button" value="1"> -->
<!--                   <input type="button" value="2"> -->
<!--                   <input type="button" value="3"> -->
                  <input type="button" value=">">
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
 		function deleteCheck(url) {
 			if(confirm("삭제하시겠습니까?")) {
 				location.href = url;
 			}
 		}
 		function updateCheck(url) {
			location.href = url;
 		}
 		function showUpdateForm(obj, replyContent){
			obj.parentElement.parentElement.nextElementSibling.style.display="";
		}
 		function deleteReply(url) {
 			if(confirm("삭제하시겠습니까?")) {
 				location.href = url;
 			}
		}
      </script>
   </body>
</html>