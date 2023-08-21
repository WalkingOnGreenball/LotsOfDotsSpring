<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
         <div>
            <a href=""><img src="/resources/images/Icons/corner_right_up_icon.png" alt="clickUp" class="clickUp"></a>
         </div>

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
               <form action="/notice/insert.do" method="post">
                  <section class="dots_NoticeTableBox">
                     <div class="dots_NoticeSubTitle">
                        <input type="text" name="noticeSubject" id="" placeholder="제목">
                     </div>
                     <div class="dots_NoticeSubWrite">
                           <textarea name="noticeContent" id="" cols="154" rows="20" placeholder="내용을 입력하세요."></textarea>
<!--                         <p> -->
<!--                            후원일정 안내드립니다. -->
<!--                            <br> -->
<!--                            <br> -->
<!--                            <br> -->
<!--                            · 5/15 1차 가제본 및 샘플 제작 <br> -->
<!--                            · 6/5 펀딩 시작일 <br> -->
<!--                            · 6/15 2차 가제본 및 굿즈 샘플 보완 작업 <br> -->
<!--                            · 7/5 펀딩 종료일 <br> -->
<!--                            · 7/22 최종 인쇄 및 제본, 굿즈 발주 <br> -->
<!--                            · 7/31 포장 및 배송 접수 <br> -->
<!--                            · 8/7 리워드 수령 <br> -->
<!--                            <br> -->
<!--                            <br> -->
<!--                            자세한 내용은 링크를 확인해주세요! -->
<!--                            <br> -->
<!--                            <br> -->
<!--                            <a href="https://tumblbug.com/lotsofdotsproject1">https://tumblbug.com/lotsofdotsproject1</a> -->
<!--                         </p> -->
                     </div>
                     <div class="dots_NoticeSubDate">
<%--                         ${ notice.noticeDate } --%>
                     </div>
                  </section>
                  <div class="dots_NoticeList">
                     <input type="submit" value="    글쓰기    ">
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