<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 16/02/2023
  Time: 8:49 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Minton - Responsive Admin Dashboard Template</title>
    <jsp:include page="layout/meta-head.jsp"></jsp:include>

</head>
<body>

<c:if test="${requestScope.message == 'delete'}">
    <script>
        window.onload = () => {
            Swal.fire("Deleted!", "Your file has been deleted.", "success")
        }
    </script>
</c:if>
<div class="container"style="margin-top: 80px">

    <div class="row">
        <div class="col-lg-12">
            <div class="card-box">
                <h4 class="page-title">Products List</h4>

                <div class="form-group row">
                    <div class="col-6">
                        <button class="btn btn-primary"><a href="/product?action=create" style="text-decoration: none; color: white">Create</a></button>
                    </div>
                    <div class="col-6">
                        <form method="get" class="row" style="justify-content: flex-end; align-items: center">
                            <input type="text" name="kw" class="form-control mr-2" placeholder="search" style="width:200px;display: inline-block">
                            <button type="submit" class="btn btn-primary">Search</button>
                        </form>
                    </div>

                </div>
                <div class="table-responsive text-center">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Product Name</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Description</th>
                            <th>Color</th>
                            <th>Category</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.products}" var="p">
                        <tr>
                            <th scope="row">${p.getId()}</th>
                            <td>${p.getName()}</td>
                            <td>${p.getPrice()}</td>
                            <td>${p.getQuantity()}</td>
                            <td>${p.getColor()}</td>
                            <td>${p.getDescription()}</td>
                            <td>
                                <c:forEach var="pCategory" items="${requestScope.categories}">
                                    <c:if test="${pCategory.getId() == p.getCategoryId()}">${pCategory.getName()}</c:if>
                                </c:forEach>
                            </td>
                            <td>
                                <a href="/product?action=edit&id=${p.getId()}" style="margin-right: 5px"><i
                                        class="fa-solid fa-pen-to-square"></i></a>
                                <a onclick="handleClick(${p.getId()})" style="cursor: pointer; color: #7266ba"><i
                                        class="fa-solid fa-trash"></i></a>
                            </td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <form action="/product?action=delete" method="post" id="deleteForm">
                        <input type="hidden" name="idDelete" id="idDelete" value="">
                    </form>
                </div>
            </div> <!-- end card-box -->
        </div> <!-- end col -->
    </div>
</div> <!-- container -->

<script>
    function handleClick(idCustomer) {
        document.getElementById("idDelete").value = idCustomer;
        Swal.fire({
            title: "Are you sure?",
            text: "You won't be able to revert this!",
            type: "warning",
            showCancelButton: !0,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes, delete it!"
        }).then(function (t) {
            document.getElementById("deleteForm").submit();
            t.value
        })
    }

</script>
<!-- Vendor js -->
<script src="/assets\js\vendor.min.js"></script>

<!-- Sweet Alerts js -->
<script src="/assets\libs\sweetalert2\sweetalert2.min.js"></script>

<!-- Sweet alert init js-->
<script src="/assets\js\pages\sweet-alerts.init.js"></script>

<!-- App js -->
<script src="/assets\js\app.min.js"></script>
<script src="/assets/js/sweetalert2.min.js"></script>
</body>
</html>
