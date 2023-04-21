/**
 *
 */
"use strict";

$(function () {
  //定数として取得
  const tentativeCategoryId = $("#tentative-category-id").val();

  // 親カテゴリが変更されたら実行
  $("#parent-category-id").on("change", function () {
    const parentId = $("#parent-category-id").val();

    //必ず実施
    $("#input-category-name").remove();
    removeSelectChildCategory();
    removeParentCategoryIdMessage();
    removeChildCategoryIdMessage();
    disabledButton();
    //未選択・新規カテゴリの場合
    if (parentId == false) {
      showParentCategoryIdMessage();
      return;
    } else if (parentId == tentativeCategoryId) {
      childCategoryIdIsTentativeCategoryId();
      showInputCategoroyName();
      return;
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

        const baseDiv = document.getElementById("select-child-category-area");
        //
        let div1 = document.createElement("div");
        div1.className = "form-group";
        div1.id = "select-child-category";
        //
        let label = document.createElement("label");
        label.htmlFor = "child-category-id";
        label.className = "col-sm-2 control-label";
        label.textContent = "category[child]";
        //
        let div2 = document.createElement("div");
        div2.className = "col-sm-8";
        //
        let select = document.createElement("select");
        select.className = "form-control";
        select.id = "child-category-id";
        select.name = "childCategoryId";
        //
        let option1 = document.createElement("option");
        option1.value = "";
        option1.textContent = "-- childCategory --";
        //
        let option2 = document.createElement("option");
        option2.value = tentativeCategoryId;
        option2.textContent = "-- 子カテゴリを追加 --";
        //
        select.appendChild(option1);
        select.appendChild(option2);
        for (let i = 0; i < childCategoryList.length; i++) {
          let option = document.createElement("option");
          option.value = childCategoryList[i].id;
          option.textContent = childCategoryList[i].name;
          select.appendChild(option);
        }
        //
        let div3 = document.createElement("div");
        div3.id = "child-category-id-message-area";
        //
        div2.appendChild(select);
        div2.appendChild(div3);
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
  $(document).on("change", "#child-category-id", function () {
    const childId = $("#child-category-id").val();
    //必ず実施
    removeInputCategorName();
    removeChildCategoryIdMessage();
    disabledButton();
    //未選択・新規カテゴリの処理
    if (childId == false) {
      showChildCategoryIdMessage();
      return;
    }
    showInputCategoroyName();
  });

  // 新規カテゴリが入力されたら実行
  $(document).on("change", "#category-name", function () {
    removeCategoryNameMessage();
    disabledButton();
    const categoryName = $("#category-name").val();
    if (categoryName == false) {
      showCategoryNameIsNullMessage();
      return;
    }
    let ancestorCategoryId;
    if ($("#parent-category-id").val() == tentativeCategoryId) {
      ancestorCategoryId = null;
    } else if ($("#child-category-id").val() == tentativeCategoryId) {
      ancestorCategoryId = $("#parent-category-id").val();
    } else {
      ancestorCategoryId = $("#child-category-id").val();
    }

    $.ajax({
      url: "http://localhost:8080/big_data/api/check-category-name",
      type: "GET",
      dataType: "JSON",
      data: {
        categoryName: categoryName,
        categoryId: ancestorCategoryId,
      },

      async: true,
    })
      .done(function (response) {
        if (!response) {
          const baseDiv = document.getElementById("category-name-message-area");
          let div = document.createElement("div");
          div.className = "text-danger";
          div.id = "category-name-message";
          div.textContent =
            "このカテゴリ名は使用できません。[既に使用済みのカテゴリ名です]";
          baseDiv.appendChild(div);
        } else {
          document.getElementById("submit-btn").disabled = false;
        }
      })
      .fail(function (XMLHttpRequest, textStatus, errorThrown) {
        // 検索失敗時には、その旨をダイアログ表示

        console.log("XMLHttpRequest : " + XMLHttpRequest.status);
        console.log("textStatus     : " + textStatus);
        console.log("errorThrown    : " + errorThrown.message);
      });
  });

  //新規カテゴリ入力フォーム表示
  function showInputCategoroyName(categoryLevelName) {
    //カテゴリ
    const baseDiv = document.getElementById("input-category-name-area");
    //新規子カテゴリ入力欄
    let div1 = document.createElement("div");
    div1.className = "form-group";
    div1.id = "input-category-name";
    //
    let label = document.createElement("label");
    label.htmlFor = "category-name";
    label.className = "col-sm-2 control-label";
    label.textContent = "新規カテゴリ名";
    //
    let div2 = document.createElement("div");
    div2.className = "col-sm-8";
    //
    let input = document.createElement("input");
    input.type = "text";
    input.className = "form-control";
    input.id = "category-name";
    input.name = "categoryName";
    //
    let div3 = document.createElement("div");
    div3.id = "category-name-message-area";
    //
    div2.appendChild(input);
    div2.appendChild(div3);
    div1.appendChild(label);
    div1.appendChild(div2);
    baseDiv.appendChild(div1);
  }
  //新規カテゴリ入力フォームを削除
  function removeInputCategorName() {
    const removeDiv = document.getElementById("input-category-name");
    if (removeDiv != null) {
      removeDiv.remove();
    }
  }
  //親カテゴリIDのエラーメッセージを削除
  function removeParentCategoryIdMessage() {
    const removeDiv = document.getElementById("parent-category-id-message");
    if (removeDiv != null) {
      removeDiv.remove();
    }
  }

  //親カテゴリIDのエラーメッセージを表示
  function showParentCategoryIdMessage() {
    const baseDiv = document.getElementById("parent-category-id-message-area");
    let div = document.createElement("div");
    div.id = "parent-category-id-message";
    div.className = "text-danger";
    div.textContent = "選択必須項目です";
    baseDiv.appendChild(div);
  }

  //子カテゴリIDのエラーメッセージを削除
  function removeChildCategoryIdMessage() {
    const removeDiv = document.getElementById("child-category-id-message");
    if (removeDiv != null) {
      removeDiv.remove();
    }
  }

  //子カテゴリIDのエラーメッセージを表示
  function showChildCategoryIdMessage() {
    const baseDiv = document.getElementById("child-category-id-message-area");
    let div = document.createElement("div");
    div.id = "child-category-id-message";
    div.className = "text-danger";
    div.textContent = "選択必須項目です";
    baseDiv.appendChild(div);
  }

  //子カテゴリ選択リスト削除
  function removeSelectChildCategory() {
    const removeDiv = document.getElementById("select-child-category");
    if (removeDiv != null) {
      removeDiv.remove();
    }
  }

  //新規カテゴリ名のエラーメッセージを削除;
  function removeCategoryNameMessage() {
    const removeDiv = document.getElementById("category-name-message");
    if (removeDiv != null) {
      removeDiv.remove();
    }
  }

  //
  function showCategoryNameIsNullMessage() {
    const baseDiv = document.getElementById("category-name-message-area");
    let div = document.createElement("div");
    div.className = "text-danger";
    div.id = "category-name-message";
    div.textContent = "入力必須項目です";
    baseDiv.appendChild(div);
  }

  //
  function disabledButton() {
    document.getElementById("submit-btn").disabled = true;
  }
  //
  function childCategoryIdIsTentativeCategoryId() {
    const baseDiv = document.getElementById("select-child-category-area");
    //
    let div = document.createElement("div");
    div.id = "select-child-category";
    //
    let input = document.createElement("input");
    input.type = "hidden";
    input.name = "childCategoryId";
    input.value = tentativeCategoryId;
    div.appendChild(input);
    baseDiv.appendChild(div);
  }
});
