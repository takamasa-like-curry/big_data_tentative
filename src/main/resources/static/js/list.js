/**
 *
 */
"use strict";

$(function () {
  // 親カテゴリが変更されたら実行
  $("#parent-category-id").on("change", function () {
    const parentCategoryId = $("#parent-category-id").val();
    //子・孫カテゴリは非表示に
    $("#child-category-id").remove();
    $("#grand-child-category-id").remove();
    if (parentCategoryId == false) {
      return;
    }

    $.ajax({
      url: "http://localhost:8080/big_data/pick-up-category-list/child-category",
      type: "GET",
      dataType: "JSON",
      data: {
        parentId: parentCategoryId,
      },

      async: true,
    })
      .done(function (data) {
        //成功
        const childCategoryList = data.childCategoryList;
        if (childCategoryList.length == 0) {
          return;
        }

        //子カテゴリ選択リストを生成
        const baseDiv = document.getElementById("child-category");
        //
        let select = document.createElement("select");
        select.className = "form-control";
        select.id = "child-category-id";
        select.name = "childCategoryId";
        //
        let defaultOption = document.createElement("option");
        defaultOption.value = "";
        defaultOption.textContent = "- child -";
        //
        select.appendChild(defaultOption);
        for (let i = 0; i < childCategoryList.length; i++) {
          const category = childCategoryList[i];
          let option = document.createElement("option");
          option.value = category.id;
          option.textContent = category.name;
          select.appendChild(option);
        }
        //
        baseDiv.appendChild(select);
      })
      .fail(function (XMLHttpRequest, textStatus, errorThrown) {
        // 検索失敗時には、その旨をダイアログ表示

        console.log("XMLHttpRequest : " + XMLHttpRequest.status);
        console.log("textStatus     : " + textStatus);
        console.log("errorThrown    : " + errorThrown.message);
      });
  });

  // 子カテゴリが変更されたら実行
  $(document).on("change", "#child-category-id", function () {
    const childCategoryId = $("#child-category-id").val();

    $("#grand-child-category-id").remove();

    if (childCategoryId == false) {
      return;
    }

    $.ajax({
      url: "http://localhost:8080/big_data/pick-up-category-list/grand-child-category",
      type: "GET",
      dataType: "JSON",
      data: {
        childId: childCategoryId,
      },

      async: true,
    })
      .done(function (data) {
        const grandChildCategoryList = data.grandChildCategoryList;
        if (grandChildCategoryList.length == 0) {
          return;
        }
        //孫カテゴリ選択リストを生成
        const baseDiv = document.getElementById("grand-child-category");
        //
        let select = document.createElement("select");
        select.className = "form-control";
        select.id = "grand-child-category-id";
        select.name = "grandChildCategoryId";
        //
        let defaultOption = document.createElement("option");
        defaultOption.value = "";
        defaultOption.textContent = "- grandChild -";
        //
        select.appendChild(defaultOption);
        for (let i = 0; i < grandChildCategoryList.length; i++) {
          const category = grandChildCategoryList[i];
          let option = document.createElement("option");
          option.value = category.id;
          option.textContent = category.name;
          select.appendChild(option);
        }
        //
        baseDiv.appendChild(select);
      })
      .fail(function (XMLHttpRequest, textStatus, errorThrown) {
        // 検索失敗時には、その旨をダイアログ表示

        console.log("XMLHttpRequest : " + XMLHttpRequest.status);
        console.log("textStatus     : " + textStatus);
        console.log("errorThrown    : " + errorThrown.message);
      });
  });
});
