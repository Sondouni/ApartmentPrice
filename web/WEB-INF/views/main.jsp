<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <style>
        table, tr, td, th {
            border: 1px solid black;
            border-collapse: collapse;
        }
    </style>
</head>
<body>
    <h1>MAIN</h1>
        <div>
            <form id="searchFrm">
                <div>
                    <label>
                        연도 :
                        <select name="year">
                            <c:forEach var="year" begin="2000" end="2020">
                                <option value="${year}">${year}년</option>
                            </c:forEach>
                        </select>
                    </label>

                    <label>
                        월 :
                        <select name="month">
                            <c:forEach var="month" begin="1" end="12">
                                <option value="${month}">${month}</option>
                            </c:forEach>
                        </select>
                    </label>
                    <label>
                        지역 :
                        <select name="excd" id="excdSl">
                            <c:forEach var="item" items="${requestScope.list}">
                                <option value="${item.ex_cd}">${item.loca_nm}</option>
                            </c:forEach>
                        </select>
                        <select name="locationcode" id="locationSl" style="display: none" >
                            <c:forEach var="item" items="${requestScope.list}">
                                <option value="${item.loca_cd}">${item.ex_cd}</option>
                            </c:forEach>
                        </select>
                    </label>
                    <input type="submit" value="검색">
                </div>
            </form>
            <div id="showTable">

            </div>
        </div>

<script src="/res/main.js"></script>
</body>
</html>