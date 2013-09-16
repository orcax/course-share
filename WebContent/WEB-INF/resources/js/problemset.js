Ext.onReady(function() {

  var borderColor = '#13967e';
  var backColor = '#41ab93';

  /**
   * Read problems from server filtered by some conditions.
   */
  var fetchProblems = function(problemType, difficulty, problemContent,
      knowledge) {
    // Ext.MessageBox.wait('Loading......', '');
    var mask = new Ext.LoadMask(Ext.getCmp('problemset-panel').el, {
      msg : 'Loading...'
    });
    mask.show();
    var params = {};
    if (problemType != undefined && problemType.length > 0) {
      params['problem_type'] = problemType;
    }
    if (difficulty != undefined && difficulty.length > 0) {
      params['difficulty'] = difficulty;
    }
    if (problemContent != undefined && problemContent.length > 0) {
      params['problem_content'] = problemContent;
    }
    if (knowledge != undefined && knowledge.length > 0) {
      params['knowledge'] = knowledge;
    }
    var panel = Ext.getCmp('problem-list-form');
    panel.removeAll();
    Ext.Ajax.request({
      url : root + '/problemset/list',
      method : 'get',
      params : params,
      success : function(resp) {
        // Ext.MessageBox.hide();
        mask.hide();
        var problems = Ext.JSON.decode(resp.responseText);
        var items = [];
        for ( var i in problems) {
          items.push(makeProblemTemplate(problems[i]));
        }
        panel.add(items);
      },
      failure : function() {
        mask.hide();
        // Ext.MessageBox.hide();
      }
    });
  };

  var makeDifficulty = function(difficulty) {
    switch (difficulty) {
    case 1:
      return '<span class="mystyle_all mystyle_blue" title="难度1级">'
          + difficulty + '</span>';
    case 2:
      return '<span class="mystyle_all mystyle_bluegreen" title="难度2级">'
          + difficulty + '</span>';
    case 3:
      return '<span class="mystyle_all mystyle_green" title="难度3级">'
          + difficulty + '</span>';
    case 4:
      return '<span class="mystyle_all mystyle_yellow" title="难度4级">'
          + difficulty + '</span>';
    case 5:
      return '<span class="mystyle_all mystyle_red" title="难度5级">' + difficulty
          + '</span>';
    }
    return '';
  };

  /**
   * Generate template for each problem object.
   */
  var makeProblemTemplate = function(problem) {
    return {
      xtype : 'panel',
      id : 'problem_' + problem['id'],
      layout : 'column',
      width : 838,
      bodyStyle : {
        borderColor : borderColor,
        borderTop : 0,
        borderRight : 0,
        borderLeft : 0
      },
      defaultType : 'panel',
      defaults : {
        border : false,
        bodyPadding : '15 5 5 5'
      },
      items : [
          {
            width : 500,
            html : problem['problemContent']
          },
          {
            width : 150,
            html : problem['knowledge']
          },
          {
            width : 90,
            html : '<span>难度：</span>' + makeDifficulty(problem['difficulty'])
          },
          {
            columnWidth : 1,
            bodyPadding: '15 0 5 0',
            html : '<span class="mystyle_all mystyle_grey">'
                + problem['problemType'] + '</span>'
          } ]
    };
  };

  var getFilterContent = function() {
    var content = Ext.getCmp('problem-search').getValue();
    var knowledge = Ext.getCmp('knowledge-search').getValue();
    var typeForm = Ext.getCmp('problem-type-form').getForm();
    var diffForm = Ext.getCmp('difficulty-form').getForm();
    var types = [];
    for ( var i in typeForm.getValues()) {
      if (i != 'type_all') {
        types.push(i);
      }
    }
    types = types.length > 0 ? types.join(',') : '';
    var diffs = [];
    for ( var i in diffForm.getValues()) {
      if (i != 'diff_all') {
        diffs.push(i);
      }
    }
    diffs = diffs.length > 0 ? diffs.join(',') : '';
    fetchProblems(types, diffs, content, knowledge);
  };

  var searchPanel = {
    xtype : 'panel',
    region : 'north',
    margin : 0,
    bodyPadding : 15,
    bodyStyle : {
      borderColor : borderColor,
      borderTop : 0,
      borderRight : 0,
      borderLeft : 0,
      background : backColor
    },
    layout : 'column',
    items : [ {
      xtype : 'textfield',
      id : 'knowledge-search',
      name : 'knowledgeSearch',
      width : 300,
      height : 30,
      emptyText : '输入知识点（空白为所有知识点）',
      listeners : {
        specialkey : function(f, e) {
          if (e.getKey() == e.ENTER) {
            getFilterContent();
          }
        }
      }
    }, {
      xtype : 'textfield',
      id : 'problem-search',
      name : 'problemSearch',
      width : 400,
      height : 30,
      margin : '0 0 0 10',
      emptyText : '输入题目关键词',
      listeners : {
        specialkey : function(f, e) {
          if (e.getKey() == e.ENTER) {
            getFilterContent();
          }
        }
      }
    }, {
      xtype : 'button',
      //baseCls: 'button_yellow',
      height : 30,
      width : 120,
      margin : '0 0 0 10',
      padding : 8,
      text : '查询题目',
      handler : getFilterContent
    }, {
      xtype : 'panel',
      columnWidth : 1,
      border : false
    } ]
  };

  var problemTypeForm = {
    xtype : 'form',
    id : 'problem-type-form',
    width : 120,
    bodyPadding : '10 15 10 15',
    bodyStyle : {
      borderColor : borderColor,
      borderRight : 0,
      borderBottom : 0,
      borderLeft : 0,
      background : backColor
    },
    margin: '15 0 0 0',
    defaultType : 'checkboxfield',
    defaults : {
      cls : 'checkbox_white',
      listeners : {
        change : function(field, newValue, oldValue, opts) {
          var form = Ext.getCmp('problem-type-form');
          if (field.name == 'type_all' && newValue) {
            form.queryById('type_concept').setValue(false);
            form.queryById('type_blankfill').setValue(false);
            form.queryById('type_choice').setValue(false);
            form.queryById('type_question').setValue(false);
            form.queryById('type_integrate').setValue(false);
            return;
          }
          form.queryById('type_all').setValue(false);
          var values = form.getForm().getValues();
          var types = [];
          for ( var i in values) {
            types.push(i);
          }
          if (types.length == 0) {
            form.queryById('type_all').setValue(true);
          }
        }
      }
    },
    items : [ {
      boxLabel : '所有题型',
      id : 'type_all',
      name : 'type_all',
      checked : true
    }, {
      boxLabel : '概念题',
      id : 'type_concept',
      name : 'type_concept'
    }, {
      boxLabel : '填空题',
      id : 'type_blankfill',
      name : 'type_blankfill'
    }, {
      boxLabel : '选择题',
      id : 'type_choice',
      name : 'type_choice'
    }, {
      boxLabel : '问答题',
      id : 'type_question',
      name : 'type_question'
    }, {
      boxLabel : '综合题',
      id : 'type_integrate',
      name : 'type_integrate'
    } ]
  };

  var difficultyForm = {
    xtype : 'form',
    id : 'difficulty-form',
    width : 120,
    bodyStyle : {
      borderColor : borderColor,
      borderRight : 0,
      borderBottom : 0,
      borderLeft : 0,
      background : backColor
    },
    bodyPadding : '10 15 0 15',
    defaultType : 'checkboxfield',
    defaults : {
      cls : 'checkbox_white',
      listeners : {
        change : function(field, newValue, oldValue, opts) {
          var form = Ext.getCmp('difficulty-form');
          if (field.name == 'diff_all' && newValue) {
            form.queryById('diff1').setValue(false);
            form.queryById('diff2').setValue(false);
            form.queryById('diff3').setValue(false);
            form.queryById('diff4').setValue(false);
            form.queryById('diff5').setValue(false);
            return;
          }
          form.queryById('diff_all').setValue(false);
          var values = form.getForm().getValues();
          var types = [];
          for ( var i in values) {
            types.push(i);
          }
          if (types.length == 0) {
            form.queryById('diff_all').setValue(true);
          }
        }
      }
    },
    items : [ {
      boxLabel : '所有难度',
      id : 'diff_all',
      name : 'diff_all',
      checked : true
    }, {
      boxLabel : '难度：1级',
      id : 'diff1',
      name : '1'
    }, {
      boxLabel : '难度：2级',
      id : 'diff2',
      name : '2'
    }, {
      boxLabel : '难度：3级',
      id : 'diff3',
      name : '3'
    }, {
      boxLabel : '难度：4级',
      id : 'diff4',
      name : '4'
    }, {
      boxLabel : '难度：5级',
      id : 'diff5',
      name : '5'
    } ]
  };

  var filterForm = {
    xtype : 'panel',
    bodyStyle : {
      borderColor : borderColor,
      borderTop : 0,
      borderBottom : 0,
      borderLeft : 0,
      background : backColor
    },
    bodyPadding: '15 0 0 0',
    region : 'west',
    width : 120,
    items : [ {
      xtype : 'button',
      //baseCls: 'button_yellow',
      width : 90,
      margin : '0 0 0 15',
      text : '过滤题目',
      handler : getFilterContent
    }, problemTypeForm, difficultyForm ]
  };

  var listForm = {
    xtype : 'form',
    id : 'problem-list-form',
    region : 'center',
    border : 0,
    overflowY : 'auto',
    listeners : {
      afterrender : function(comp, opts) {
        fetchProblems();
      }
    }
  };

  /**
   * Problem Set Panel (Main)
   */
  Ext.create('Ext.panel.Panel', {
    id : 'problemset-panel',
    layout : 'border',
    bodyStyle : {
      borderColor : borderColor,
      borderTop : 0,
      borderBottom : 0,
      background : backColor
    },
    height : Ext.get('main-content').getHeight(),
    renderTo : 'main-content',
    items : [ searchPanel, filterForm, listForm ]
  });
});