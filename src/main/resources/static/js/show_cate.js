/**
 *
 */
"use strict";

$(function () {
  // 親カテゴリが変更されたら実行
  $("#parent-id").on("change", function () {
    const parentId = $("#parent-id").val();

    if (parentId == -1) {
      $("#input-parent-category").remove();
      $("#input-child-category").remove();
      $("#select-child-category").remove();
      $("#input-grand-child-category").remove();
      return;
    } else if (parentId == -2) {
      $("#input-parent-category").remove();
      $("#input-child-category").remove();
      $("#select-child-category").remove();
      $("#input-grand-child-category").remove();
      showInputParentCategory();
      showInputChildCategory();
      showInputGrandChildCategory();
      return;
    } else {
      $("#input-parent-category").remove();
      $("#input-child-category").remove();
      $("#select-child-category").remove();
      $("#input-grand-child-category").remove();
    }

    $.ajax({
      url: "http://localhost:8080/big_data/pick-up-category-list/child-category",
      type: "GET",
      dataType: "JSON",
      data: {
        parentId: parentId,
      },

      async: true,
    })
      .done(function (data) {
        //成功
        const childCategoryList = data.childCategoryList;

        if (childCategoryList.length == 0) {
          $("#child-id").hide();
          $("#grand-child-id").hide();
          return;
        }

        const baseDiv = document.getElementById("category");
        //
        let div1 = document.createElement("div");
        div1.className = "form-group";
        div1.id = "select-child-category";
        //
        let label = document.createElement("label");
        label.htmlFor = "child-id";
        label.className = "col-sm-2 control-label";
        label.textContent = "category[child]";
        //
        let div2 = document.createElement("div");
        div2.className = "col-sm-8";
        //
        let select = document.createElement("select");
        select.className = "form-control";
        select.id = "child-id";
        select.name = "childId";
        //
        let option1 = document.createElement("option");
        option1.value = "-1";
        option1.textContent = "-- childCategory --";
        //
        let option2 = document.createElement("option");
        option2.value = "-2";
        option2.textContent = "-- NewCategory --";
        //
        select.appendChild(option1);
        select.appendChild(option2);
        for (let i = 0; i < childCategoryList.length; i++) {
          let option = document.createElement("option");
          option.value = childCategoryList[i].id;
          option.textContent = childCategoryList[i].name;
          select.appendChild(option);
        }
        div2.appendChild(select);
        div1.appendChild(label);
        div1.appendChild(div2);
        baseDiv.appendChild(div1);
      })
      .fail(function (XMLHttpRequest, textStatus, errorThrown) {
        // 検索失敗時には、その旨をダイアログ表示

        console.log("XMLHttpRequest : " + XMLHttpRequest.status);
        console.log("textStatus     : " + textStatus);
        console.log("errorThrown    : " + errorThrown.message);
      });
  });

  // 子カテゴリが変更されたら実行
  $(document).on("change", "#child-id", function () {
    const childId = $("#child-id").val();

    if (childId == -1) {
      $("#input-child-category").remove();
      $("#input-grand-child-category").remove();
      return;
    }
    if (childId == -2) {
      $("#input-child-category").remove();
      $("#input-grand-child-category").remove();
      showInputChildCategory();
      showInputGrandChildCategory();
      return;
    } else {
      $("#input-child-category").remove();
      $("#input-grand-child-category").remove();
      showInputGrandChildCategory();
    }
  });

  // 子カテゴリが入力されたら実行
  $(document).on("change", "#child-category-name", function () {
    console.log("イベント感知までは正常");
    const categoryName = $("#child-category-name").val();

    $.ajax({
      url: "http://localhost:8080/big_data/api/check-child-category",
      type: "GET",
      dataType: "JSON",
      data: {
        categoryName: categoryName,
      },

      async: true,
    })
      .done(function (response) {
        console.log(response);
      })
      .fail(function (XMLHttpRequest, textStatus, errorThrown) {
        // 検索失敗時には、その旨をダイアログ表示

        console.log("XMLHttpRequest : " + XMLHttpRequest.status);
        console.log("textStatus     : " + textStatus);
        console.log("errorThrown    : " + errorThrown.message);
      });
  });

  function showInputParentCategory() {
    //カテゴリ
    const baseDiv = document.getElementById("category");
    //新規親カテゴリ入力欄
    let inputParentCategoryDiv = document.createElement("div");
    inputParentCategoryDiv.className = "form-group";
    inputParentCategoryDiv.id = "input-parent-category";
    //
    let label = document.createElement("label");
    label.className = "col-sm-2 control-label";
    label.htmlFor = "parentCategoryName";
    label.textContent = "新規カテゴリ名[parent]";
    //
    let inputDiv = document.createElement("div");
    inputDiv.className = "col-sm-8";
    //
    let input = document.createElement("input");
    input.className = "form-control";
    input.name = "parentCategoryName";
    //生成
    inputDiv.appendChild(input);
    inputParentCategoryDiv.appendChild(label);
    inputParentCategoryDiv.appendChild(inputDiv);
    //挿入
    baseDiv.appendChild(inputParentCategoryDiv);
  }

  function showInputChildCategory() {
    //カテゴリ
    const baseDiv = document.getElementById("category");
    //新規子カテゴリ入力欄
    let inputChildCategoryDiv = document.createElement("div");
    inputChildCategoryDiv.className = "form-group";
    inputChildCategoryDiv.id = "input-child-category";
    //
    let label = document.createElement("label");
    label.className = "col-sm-2 control-label";
    label.htmlFor = "childCategoryName";
    label.textContent = "新規カテゴリ名[child]";
    //
    let inputDiv = document.createElement("div");
    inputDiv.className = "col-sm-8";
    //
    let input = document.createElement("input");
    input.className = "form-control";
    input.name = "childCategoryName";
    input.id = "child-category-name";
    //生成
    inputDiv.appendChild(input);
    inputChildCategoryDiv.appendChild(label);
    inputChildCategoryDiv.appendChild(inputDiv);
    //挿入
    baseDiv.appendChild(inputChildCategoryDiv);
  }

  function showInputGrandChildCategory() {
    //カテゴリ
    const baseDiv = document.getElementById("category");
    //新規子カテゴリ入力欄
    let inputGrandChildCategoryDiv = document.createElement("div");
    inputGrandChildCategoryDiv.className = "form-group";
    inputGrandChildCategoryDiv.id = "input-grand-child-category";
    //
    let label = document.createElement("label");
    label.className = "col-sm-2 control-label";
    label.htmlFor = "grandChildCategoryName";
    label.textContent = "新規カテゴリ名[grandChild]";
    //
    let inputDiv = document.createElement("div");
    inputDiv.className = "col-sm-8";
    //
    let input = document.createElement("input");
    input.className = "form-control";
    input.name = "grandChildCategoryName";
    //生成
    inputDiv.appendChild(input);
    inputGrandChildCategoryDiv.appendChild(label);
    inputGrandChildCategoryDiv.appendChild(inputDiv);
    //挿入
    baseDiv.appendChild(inputGrandChildCategoryDiv);
  }
});
