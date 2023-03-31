"use strict";

$(function () {
  // 親カテゴリが変更されたら実行
  $("#next-page").on("click", function () {
    console.log("実行開始");

    const name = $("#name").val();
    const parentId = $("#parent-id").val();
    const childId = $("#child-id").val();
    const grandChildId = $("#grand-child-id").val();
    const brand = $("#brand").val();

    // if (parentId == -1) {
    //   $("#child-id").hide();
    //   $("#grand-child-id").hide();
    //   return;
    // }
    console.log(brand);

    $.ajax({
      url: "http://localhost:8080/big_data/page/next",
      type: "GET",
      dataType: "JSON",
      data: {
        name: name,
        parentId: parentId,
        childId: childId,
        grandChildId: grandChildId,
        brand: brand,
      },

      async: true,
    })
      .done(function (data) {
        //成功
        const itemList = data.itemList;
        console.log(itemList);

        // if (childCategoryList.length == 0) {
        //   $("#child-id").hide();
        //   $("#grand-child-id").hide();
        //   return;
        // }

        元データを削除;
        $("#item-table-main").children().remove();
        //子カテゴリを挿入
        let tbody = document.createElement("tbody");
        let tr = document.createElement("tr");
        let td = document.createElement("td");
        let a = document.createElement("a");

        td.className = "item-name";
        a.href = "/big_data/detail?itemId=0&amp;page=null";
        a.innerText = "MLB Cincinnati Reds T Shirt Size XL";
      })
      .fail(function (XMLHttpRequest, textStatus, errorThrown) {
        // 検索失敗時には、その旨をダイアログ表示

        console.log("XMLHttpRequest : " + XMLHttpRequest.status);
        console.log("textStatus     : " + textStatus);
        console.log("errorThrown    : " + errorThrown.message);
      });
  });
});
