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
      <link rel="stylesheet" href="/resources/css/member/myInfo.css">
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
                     <a href="/category/dots/dots.jsp"><img src="/resources/images/Icons/circle_icon.png" alt="Dots" class="icons"></a>
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
         <main class="project_Main">
            <section class="Login_Title">${ sessionScope.memberName }님 환영합니다!</section>
            <section class="Login_Form">
               <fieldset>
                  <legend></legend>
                     <form action="/member/update.do" method="post">
                        <div class="login_idPw">
                           ID : <input type="text" name=memberId placeholder=" ${ member.memberId }" class="Login_Blank" required value="${ member.memberId }"readonly><br>
                           PW : <input type="password" name="memberPw" placeholder="비밀번호를 입력해주세요." class="Login_Blank" required><br>
                           PW-Check : <input type="password" name="memberPwCheck" placeholder="비밀번호를 입력해주세요." class="Login_Blank" required><br>
                           NAME : <input type="text" name="memberName" placeholder=" ${ member.memberName }" class="Login_Blank" required value="${ member.memberName }"readonly><br>
                           ADDRESS : <input type="text" id="memberAddress" name="memberAddress"  placeholder=" ${ member.memberAddress }" class="Login_Blank" required value="${ member.memberAddress }"><br>
                           <input type="button" onclick="sample4_execDaumPostcode();" value="우편번호 찾기" class="Login_Blank findAddressButton"><br>
                           E-MAIL : <input type="email" name="memberEmail"  placeholder=" ${ member.memberEmail }" class="Login_Blank" required value="${ member.memberEmail }"><br>
                           PHONE : <input type="tel" name="memberPhone" placeholder=" ${ member.memberPhone }" class="Login_Blank" required value="${ member.memberPhone }"><br>
                           <div class="sign_radioBox">
                              GENDER : 
                              <input type="hidden" name="memberGender" value="${ member.memberGender }">
                              <c:if test="${ member.memberGender eq 'Male' }">&nbsp;&nbsp;&nbsp;남자</c:if>
                              <c:if test="${ member.memberGender eq 'Female' }">&nbsp;&nbsp;&nbsp;여자</c:if>
                              <c:if test="${ member.memberGender eq 'None' }">비공개</c:if>
                           </div>
                        </div>
                        <div class="login_btn">
                           <div class="logout_btn">
                           	   <a href="/member/logout.do">LOGOUT</a>
                           </div>
                           <input type="submit" value="UPDATE">
                           <br>
                           <div class="delete_btn">
	                           <a href="javascript:void(0)" onclick="checkDelete();">DELETE</a>
                           </div>
                           
                        </div>
                     </form>
               </fieldset>
            </section>
         </main>

         <!-- footer -->
         <jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
      </div>
      <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
      <script>
         function sample4_execDaumPostcode() {
            new daum.Postcode({
               oncomplete : function(data) {
                  document.querySelector("#memberAddress").value = "(" + data.zonecode + ") " + data.roadAddress + ", " + data.buildingName;
               }
            }).open();
         }
      </script>
      <script>
         document.addEventListener("DOMContentLoaded", () => {
            document.querySelector("#shopping_bag_icon").addEventListener("click", () => {
                  document.querySelector("#shopping_bag").style.display = "block";
            })
            document.querySelector("#shopping_bag_exit_icon").addEventListener("click", () => {
                  document.querySelector("#shopping_bag").style.display = "none";
            })
            
            // /member/delete.do?memberId=${ sessionScope.memberId }
         });
         function checkDelete() {
            const memberId = '${ sessionScope.memberId }';
            if(confirm("탈퇴하시겠습니까?")) {
               location.href = "/member/delete.do?memberId=" + memberId;
            }
         }
      </script>
   </body>
</html>