<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
		<div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-light" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <div class="sb-sidenav-menu-heading">Core</div>
                            <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
	                            <a class="nav-link" href="/">
	                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
	                                Dashboard
	                            </a>
                            </sec:authorize>
                            <sec:authorize access="isAnonymous()">
	                            <a class="nav-link" href="<c:url value='/user/signup'/>">
	                                <div class="sb-nav-link-icon"><i class="fas fa-sign-in-alt"></i></div>
	                                SignUp
	                            </a>
                            </sec:authorize>
                            <!-- <div class="sb-sidenav-menu-heading">Interface</div>
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayouts" aria-expanded="false" aria-controls="collapseLayouts">
                                <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                                Layouts
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseLayouts" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="#">Static Navigation</a>
                                    <a class="nav-link" href="#">Light Sidenav</a>
                                </nav>
                            </div> -->
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
	                            <a class="nav-link" href="<c:url value='/'/>">
		                            <div class="sb-nav-link-icon"><i class="fas fa-chart-area"></i></div>
		                            Chart
	                            </a>
	                            <a class="nav-link" href="<c:url value='/board/'/>">
		                            <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
		                            Board
	                            </a>
	                            <a class="nav-link" href="<c:url value='/course/'/>">
	                            	<div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
	                            	Course
	                            </a>
	                            <a class="nav-link" href="<c:url value='/chat/room'/>">
	                            	<div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
	                            	Chat
	                            </a>
	                            <a class="nav-link" href="<c:url value='/group/'/>">
	                            	<div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
	                            	Group
	                            </a>
                        </div>
                    </div>
                    <!-- <div class="sb-sidenav-footer">
                        <div class="small">Logged in as:</div>
                        Start Bootstrap
                    </div> -->
                </nav>
            </div>