"use strict";

const FIRST_PAGE = 1;

$(function () {
  //初期表示の際の処理
  pagesView();

  // nextがクリックされたら
  $(".next-page").on("click", function () {
    let page = $("#this-page").val();
    page++;
    pickUpItemList(page, null);
  });

  //prevがクリックされたら
  $(".prev-page").on("click", function () {
    let page = $("#this-page").val();
    page--;
    pickUpItemList(page, null);
  });

  // serchがクリックされたら;
  $("#serch-btn").on("click", function () {
    pickUpItemList(FIRST_PAGE, null);
  });

  // goがクリックされたら;
  $("#page-jump-btn").on("click", function () {
    let page = $("#this-page").val();
    pickUpItemList(page, null);
  });

  // brandがクリックされたら;
  $(".brand-btn").on("click", function () {
    const brand = $(this).text();
    pickUpItemList(FIRST_PAGE, brand);
    $("#brand").val(brand);
  });
});

function pagesView(page) {
  if (page == null) {
    page = $("#this-page").val();
  }
  const totalPage = $("#total-page").text();

  if (page == 1 && totalPage == 1) {
    $(".previous").hide();
    $(".next").hide();
  } else if (page == 1) {
    $(".previous").hide();
    $(".next").show();
  } else if (page > 1 && page < totalPage) {
    $(".previous").show();
    $(".next").show();
  } else if ((page = totalPage)) {
    $(".previous").show();
    $(".next").hide();
  } else {
    console.log("ページ数について何らかのエラーが発生しています。");
    //エラー処理
  }
}

function pickUpItemList(page, brand) {
  const name = $("#name").val();
  const parentId = $("#parent-id").val();
  const childId = $("#child-id").val();
  const grandChildId = $("#grand-child-id").val();
  if (brand == null) {
    brand = $("#brand").val();
  }

  $.ajax({
    url: "http://localhost:8080/big_data/page",
    type: "GET",
    dataType: "JSON",
    data: {
      name: name,
      parentId: parentId,
      childId: childId,
      grandChildId: grandChildId,
      brand: brand,
      page: page,
    },

    async: true,
  })
    .done(function (data) {
      //成功

      const itemList = data.pageInfo.itemList;
      // const itemList = data.itemList;

      //元データを削除;
      $("#item-table-main").children().remove();
      //データ挿入
      for (let i = 0; i < itemList.length; i++) {
        const item = itemList[i];
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
      }

      $("#this-page").val(data.pageInfo.thisPage);
      $("#total-page").text(data.pageInfo.totalPage);

      pagesView(page);
    })
    .fail(function (XMLHttpRequest, textStatus, errorThrown) {
      // 検索失敗時には、その旨をダイアログ表示

      console.log("XMLHttpRequest : " + XMLHttpRequest.status);
      console.log("textStatus     : " + textStatus);
      console.log("errorThrown    : " + errorThrown.message);
    });
}
