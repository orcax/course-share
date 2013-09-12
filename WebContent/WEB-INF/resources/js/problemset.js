Ext.onReady(function() {

  /**
   * Read problems from server filtered by some conditions.
   */
  var fetchProblems = function(problemType, problemContent) {
    Ext.MessageBox.wait('Loading......', '');
    var params = {};
    if (problemType != undefined && problemType.length > 0) {
      params['problem_type'] = problemType;
    }
    if (problemContent != undefined && problemContent.length > 0) {
      params['problem_content'] = problemContent;
    }
    var panel = Ext.getCmp('problem-list-form');
    panel.removeAll();
    Ext.Ajax.request({
      url : root + '/problemset/list',
      method : 'get',
      params : params,
      success : function(resp) {
        var problems = Ext.JSON.decode(resp.responseText);
        console.log(problems);
        for ( var i in problems) {
          var p = makeProblemTemplate(problems[i]);
          panel.add(p);
        }
        Ext.MessageBox.hide();
      },
      failure: function() {
        Ext.MessageBox.hide();
      }
    });
  };

  /**
   * Generate template for each problem object.
   */
  var makeProblemTemplate = function(problem) {
    return {
      xtype : 'panel',
      id : 'problem_' + problem['id'],
      layout : 'column',
      width : 800,
      bodyStyle : {
        borderTop : 0,
        borderRight : 0,
        borderLeft : 0,
      },
      items : [ {
        xtype : 'panel',
        border : false,
        columnWidth : 0.8,
        bodyPadding : 10,
        html : problem['problemContent']
      }, {
        xtype : 'panel',
        layout : {
          type : 'vbox',
          align : 'left'
        },
        border : false,
        columnWidth : 0.2,
        bodyPadding : 10,
        items : [ {
          xtype : 'label',
          text : '【题型】' + problem['problemType']
        }, {
          xtype : 'label',
          text : '【难度】' + problem['difficulty']
        }, {
          xtype : 'label',
          text : '【知识点】' + problem['knowledge']
        } ]
      } ]
    };
  };

  var getFilterContent = function() {
    var content = Ext.getCmp('problem-search').getValue().trim();
    var form = Ext.getCmp('problem-type-form').getForm();
    var values = form.getValues();
    var types = [];
    for ( var i in values) {
      if (i != 'type_all') {
        types.push(i);
      }
    }
    types = types.length > 0 ? types.join(',') : '';
    fetchProblems(types, content);
  };

  var searchPanel = {
    xtype : 'panel',
    region : 'north',
    margin : '0 20 0 20',
    bodyPadding : '15 0 10 0',
    bodyStyle : {
      borderTop : 0,
      borderRight : 0,
      borderLeft : 0,
    },
    layout : 'column',
    items : [ {
      xtype : 'textfield',
      id : 'problem-search',
      name : 'problemSearch',
      width : 500,
      height : 30,
      emptyText : '搜索题目'
    }, {
      xtype : 'button',
      height : 30,
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

  var filterForm = {
    xtype : 'form',
    id : 'problem-type-form',
    region : 'west',
    width : 200,
    bodyStyle : {
      borderTop : 0,
      borderBottom : 0,
      borderLeft : 0,
    },
    bodyPadding : '20 10 0 20',
    defaultType : 'checkboxfield',
    defaults : {
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
      xtype : 'button',
      text : '过滤题型',
      margin : '0 0 10 0',
      handler : getFilterContent
    }, {
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

  var listForm = {
    xtype : 'form',
    id : 'problem-list-form',
    region : 'center',
    border : 0,
    autoScroll : true,
    listeners : {
      beforerender : function(comp, opts) {
        fetchProblems();
      }
    }
  };

  /**
   * Problem Set Panel (Main)
   */
  var problemsetPanel = {
    xtype : 'panel',
    id : 'problemset-panel',
    layout : 'border',
    bodyBorder : false,
    items : [ searchPanel, filterForm, listForm ],
  };

  var contentPanel = Ext.getCmp("content-panel");
  contentPanel.removeAll();
  contentPanel.add(problemsetPanel);
});