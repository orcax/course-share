$(function(){
  
  var makeColorClass = function(type) {
    var cls = '';
    switch(type){
    case '概念题':
      cls = 'cs-frame-grey';
      break;
    case '填空题':
      cls = 'cs-frame-blue';
      break;
    case '选择题':
      cls = 'cs-frame-green';
      break;
    case '问答题':
      cls = 'cs-frame-yellow';
      break;
    case '综合题':
      cls = 'cs-frame-red';
      break;
    }
    return cls;
  };
  
  var makeProblemType = function(type) {
    return '<span class="btn problemset-item-type cs-style-grey2">' + type + '</span>';
  };
    
  var makeDifficulty = function(diff) {
    var cls = '';
    switch(diff) {
    case 1:
      cls = 'btn-primary';
      break;
    case 2:
      cls = 'btn-info';
      break;
    case 3:
      cls = 'btn-success';
      break;
    case 4:
      cls = 'btn-warning';
      break;
    case 5:
      cls = 'btn-danger';
      break;
    }
    return '<span class="btn problemset-item-diff ' + cls + '">' + diff + '</span>';
  };
  
  var url = root + 'problemset/list';
  
  var makeProblems = function(problems) {
    var list = [];
    $.each(problems, function(k, v){
      var colorCls = makeColorClass(v.problemType);
      var type = makeProblemType(v.problemType);
      var diff = makeDifficulty(v.difficulty);
      list.push([
        '<div class="problemset-item ' + colorCls + '">',
        '<div class="problemset-item-section" style="width:75px">' + type + '</div>',
        '<div class="problemset-item-section" style="width:55px">' + diff + '</div>',
        '<div class="problemset-item-section" style="width:400px">' + v.problemContent + '</div>',
        '<div class="problemset-item-section" style="width:150px">' + v.knowledge + '</div>',
        '</div>'
      ].join(''));
    });
    if(list.length > 0){
      $('#problemset-list').append(list);
    }
  };
  
//  $.getJSON(url, function(data) {
//    makeProblems(data);
//  });
  
  $("#problemset-list").scrollPagination({
    url: url,
    method: 'GET',
    param: {},
    scrollTarget: $(window),
    heightOffset: 10,
    beforeLoad: function() {
      $("#problemset-load").fadeIn();
    },
    afterLoad: function(data) {
      $("#problemset-load").fadeOut();
      makeProblems(data);
    }  
  });
  
});