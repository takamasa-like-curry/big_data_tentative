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
        // for (let i = 0; i < itemList.length; i++) {
        // $("#child-id").append(
        //   $("<option>")
        //     .val(childCategoryList[i].id)
        //     .text(childCategoryList[i].name)
        // );
        // }

        ///////////////////////////////////////////////
        let str = "";
        str += "<tr>";
        str += "<td class='item-name'>";
        str += "<a href='/big_data/detail?itemId=0&amp;page=1'>";
        str += "MLB Cincinnati Reds T Shirt Size XL";
        str += "</a>";
        str += "";
        str += "";
        str += "";
        str += "";
        // <tbody id="item-table-main">

        //     </td>
        //     <td
        //       class="item-price"
        //     >10.0</td>
        //     <td class="item-category">
        //       <span>
        //         <a
        //           href="/big_data/?categoryId=1"
        //           >Men</a
        //         >
        //         <span> / </span>
        //       </span><span>
        //         <a
        //           href="/big_data/?categoryId=2"
        //           >Tops</a
        //         >
        //         <span> / </span>
        //       </span><span>
        //         <a
        //           href="/big_data/?categoryId=3"
        //           >T-shirts</a
        //         >

        //       </span>
        //     </td>
        //     <td class="item-brand">
        //       <a
        //         href="/big_data/?selectBrand="
        //         ></a
        //       >
        //     </td>
        //     <td class="item-condition">3</td>
        //   </tr>
        // </tbody>
      })
      .fail(function (XMLHttpRequest, textStatus, errorThrown) {
        // 検索失敗時には、その旨をダイアログ表示

        console.log("XMLHttpRequest : " + XMLHttpRequest.status);
        console.log("textStatus     : " + textStatus);
        console.log("errorThrown    : " + errorThrown.message);
      });
  });
});
