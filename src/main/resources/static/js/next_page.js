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
        const tbody = document.getElementById("item-table-main");

        let tr = document.createElement("tr");
        let td = null;
        let a = null;
        let span = null;

        //名前
        td = document.createElement("td");
        a = document.createElement("a");
        td.className = "item-name";
        a.href = "/big_data/detail?itemId=" + item.itemId + "&amp;page=null";
        a.innerText = item.name;
        td.appendChild(a);
        tr.appendChild(td);
        //価格
        td = document.createElement("td");
        td.className = "item-price";
        td.textContent = "$" + item.price; //値段なのでinnerTextでなくても可
        tr.appendChild(td);
        //カテゴリ
        td = document.createElement("td");
        td.className = "item-brand";
        for (let j = 0; j < item.categoryList.length; j++) {
          const category = item.categoryList[j];
          a = document.createElement("a");
          a.href = "/big_data/category?categoryId=" + category.id;
          a.innerText = category.name;
          td.appendChild(a);
          if (j < item.categoryList.length - 1) {
            span = document.createElement("span");
            span.textContent = " / ";
            td.appendChild(span);
          }
        }
        tr.append(td);
        //ブランド
        td = document.createElement("td");
        a = document.createElement("a");
        td.className = "item-brand";
        a.className = "brand-btn";
        a.href = "/big_data/brand?brand=" + item.brand;
        a.innerText = item.brand;
        td.appendChild(a);
        tr.append(td);
        //コンディション
        td = document.createElement("td");
        td.className = "item-condition";
        td.textContent = item.condition;
        tr.appendChild(td);

        tbody.appendChild(tr);
      })
      .fail(function (XMLHttpRequest, textStatus, errorThrown) {
        // 検索失敗時には、その旨をダイアログ表示

        console.log("XMLHttpRequest : " + XMLHttpRequest.status);
        console.log("textStatus     : " + textStatus);
        console.log("errorThrown    : " + errorThrown.message);
      });
  });
});
