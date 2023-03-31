/**
 *
 */
"use strict";

$(function () {
  // console.log

  $("#back-page").on("click", function () {
    console.log("戻るボタンが押されました");
    window.history.back();
  });
});
