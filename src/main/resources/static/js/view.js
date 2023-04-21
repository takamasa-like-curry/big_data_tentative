"use strict";

$(function () {
  // if ($("#child-id").val() == -1) {
  //   $("#grand-child-id").hide();
  //   $("#input-child-category").hide();
  // }

  const thisPage = $("#this-page").val();
  const totalPage = $("#total-page").text();
  if (totalPage == 1) {
    $(".previous").hide();
    $(".next").hide();
  } else if (thisPage == 1) {
    $(".previous").hide();
    $(".next").show();
  } else if (thisPage > 1 && thisPage < totalPage) {
    $(".previous").show();
    $(".next").show();
  } else if (thisPage == totalPage) {
    $(".previous").show();
    $(".next").hide();
  } else {
    //例外処理
  }
});
