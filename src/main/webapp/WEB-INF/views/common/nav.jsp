<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
		<div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-light" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <sec:authorize access="hasRole('ADMIN')">
                            <div class="sb-sidenav-menu-heading">Admin</div>
	                            <a class="nav-link" href="/">
	                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
	                                Dashboard
	                            </a>
	                            <a class="nav-link" href="<c:url value='/admin/code'/>">
	                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
	                                CodeManagement
	                            </a>
                            </sec:authorize>
                            <!-- <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapsePages" aria-expanded="false" aria-controls="collapsePages">
                                <div class="sb-nav-link-icon"><i class="fas fa-book-open"></i></div>
                                Pages
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapsePages" aria-labelledby="headingTwo" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                                    <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#pagesCollapseAuth" aria-expanded="false" aria-controls="pagesCollapseAuth">
                                        Authentication
                                        <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                                    </a>
                                    <div class="collapse" id="pagesCollapseAuth" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordionPages">
                                        <nav class="sb-sidenav-menu-nested nav">
                                            <a class="nav-link" href="">Login</a>
                                            <a class="nav-link" href="">Register</a>
                                            <a class="nav-link" href="">Forgot Password</a>
                                        </nav>
                                    </div>
                                    <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#pagesCollapseError" aria-expanded="false" aria-controls="pagesCollapseError">
                                        Error
                                        <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                                    </a>
                                    <div class="collapse" id="pagesCollapseError" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordionPages">
                                        <nav class="sb-sidenav-menu-nested nav">
                                            <a class="nav-link" href="#">Page</a>
                                        </nav>
                                    </div>
                                </nav>
                            </div> -->
                            <div class="sb-sidenav-menu-heading">MENU</div>
	                            <sec:authorize access="isAnonymous()">
		                            <a class="nav-link" href="<c:url value='/user/signup'/>">
		                                <div class="sb-nav-link-icon"><i class="fas fa-sign-in-alt"></i></div>
		                                SignUp
		                            </a>
	                            </sec:authorize>
	                            <a class="nav-link" href="<c:url value='/board'/>">
		                            <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
		                            Board
	                            </a>
	                            <a class="nav-link" href="<c:url value='/course'/>">
	                            	<div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
	                            	Course
	                            </a>
	                            <a class="nav-link" href="<c:url value='/chat/room'/>">
	                            	<div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
	                            	Chat
	                            </a>
								<a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayouts" aria-expanded="false" aria-controls="collapseLayouts">
	                                <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
	                                Group
	                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
	                            </a>
	                            <div class="collapse" id="collapseLayouts" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
	                                <nav class="sb-sidenav-menu-nested nav">
	                                    <a class="nav-link" href="<c:url value='/group'/>">MyCourse</a>
	                                    <a class="nav-link" href="<c:url value='/course/apply'/>">ApplyCourse</a>
	                                </nav>
	                            </div>
                        </div>
                    </div>
                </nav>
            </div>