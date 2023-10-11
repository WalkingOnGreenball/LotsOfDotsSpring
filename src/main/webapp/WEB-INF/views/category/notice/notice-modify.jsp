<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
      <link rel="stylesheet" href="/resources/css/notice/notice-insert.css">
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
               <form action="/notice/modify.do" method="post" enctype="multipart/form-data">
                  <input type="hidden" name="noticeNo" value="${ notice.noticeNo }">
                  <input type="hidden" name="noticeWriter" value="${ notice.noticeWriter }">
                  <input type="hidden" name="noticeFileName" value="${ notice.noticeFileName }">
                  <input type="hidden" name="noticeFileName" value="${ notice.noticeFileRename }">
                  <input type="hidden" name="noticeFilePath" value="${ notice.noticeFilePath }">
                  <input type="hidden" name="noticeFileLength" value="${ notice.noticeFileLength }">

                  <section class="dots_NoticeTableBox">
                     <div class="dots_NoticeSubTitle">
                        <input type="text" name="noticeSubject" id="" value="${ notice.noticeSubject }">
                     </div>
                     <div class="dots_NoticeSubWrite">
                           <textarea name="noticeContent" id="" cols="154" rows="25">${ notice.noticeContent }</textarea>
                     </div>
                     <div class="dots_NoticeUploadFile">
<!--                      	<label>첨부파일</label> -->
                     	<a href="../resources/nuploadFiles/${ notice.noticeFileRename }" download> ${ notice.noticeFileName } /&nbsp;</a>
						<input type="file" name="uploadFile">
                     </div>
                  </section>
                  <div class="dots_NoticeList">
                     <input type="submit" value="    수정하기    ">
                  </div>
               </form>
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