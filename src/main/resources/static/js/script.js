function valthisform() {
  var checkboxs = document.getElementsByName("remember-me");
  var okay = false;
  for (var i = 0, l = checkboxs.length; i < l; i++) {
    if (checkboxs[i].checked) {
      okay = true;
      break;
    }
  }
  if (!okay) alert("Please check atleast 1 checkbox");
}

// function valthisform() {
//   $("form").on("click", "#required-group", function () {
//     var flag = false;
//     $("#required-group").each(function (e) {
//       if (this.checked) {
//         flag = true;
//       }
//     });
//     if (!flag) {
//       if (okay) alert("Thank you for checking a checkbox");
//       else alert("Please check a checkbox");
//     }
//   });
// }
