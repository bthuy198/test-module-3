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
<!-- Start Content-->
<div class="container-fluid">

  <div class="row">
    <div class="col-12">
      <div class="card-box">
        <h4 class="header-title">Edit product's information</h4>

        <div class="row">
          <div class="col-12">
            <div class="p-2">
              <form class="form-horizontal" method="post">
                <div class="form-group row">
                  <label class="col-sm-2 col-form-label">Product Name</label>
                  <div class="col-sm-10">
                    <input type="text" id="name" class="form-control" name="name"
                           value="${requestScope.product.getName()}">
                  </div>
                </div>
                <div class="form-group row">
                  <label class="col-sm-2 col-form-label">Price</label>
                  <div class="col-sm-10">
                    <input type="text" class="form-control" id="price" name="price"
                           value="${requestScope.product.getPrice()}">
                  </div>
                </div>
                <div class="form-group row">
                  <label class="col-sm-2 col-form-label">Quantity</label>
                  <div class="col-sm-10">
                    <input type="text" class="form-control" id="quantity"
                           name="quantity"
                           value="${requestScope.product.getQuantity()}">
                  </div>
                </div>
                <div class="form-group row">
                  <label class="col-sm-2 col-form-label">Product Name</label>
                  <div class="col-sm-10">
                    <input type="text" id="color" class="form-control" name="color"
                           value="${requestScope.product.getColor()}">
                  </div>
                </div>
                <div class="form-group row">
                  <label class="col-sm-2 col-form-label">Description</label>
                  <div class="col-sm-10">
                    <input type="text" id="description" class="form-control" name="description"
                           value="${requestScope.product.getDescription()}">
                  </div>
                </div>
                <div class="form-group row">
                  <label class="col-sm-2 col-form-label">Category</label>
                  <select class="col-sm-10 form-select custom-select"
                          aria-label="Default select example" name="idCategory"
                          id="idCategory">
                    <c:forEach var="pCategory" items="${requestScope.categories}">
                      <c:choose>
                        <c:when test="${requestScope.product.getCategoryId() == pCategory.getId()}">
                          <option value="${pCategory.getId()}" selected><c:out
                                  value="${pCategory.getName()}"></c:out></option>
                        </c:when>
                        <c:otherwise>
                          <option value="${pCategory.getId()}"><c:out
                                  value="${pCategory.getName()}"></c:out></option>
                        </c:otherwise>
                      </c:choose>
                    </c:forEach>
                  </select>
                </div>

                <div class="form-group row">
                  <div class="offset-4">
                    <button type="submit" class="btn btn-primary"
                            style="padding: 10px 40px">Edit
                    </button>
                    <button class="btn btn-primary"
                            style="padding: 10px 40px"><a href="/product" style="text-decoration: none; color: white">Cancel</a>
                    </button>
                  </div>
                </div>
              </form>
            </div>
          </div>

        </div>
        <!-- end row -->

      </div> <!-- end card-box -->
    </div><!-- end col -->
  </div>
  <!-- end row -->
</div> <!-- container -->
</body>
</html>
