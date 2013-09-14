Ext.onReady(function() {

  /**
   * Read images from server filtered by some conditions.
   */
  var fetchImages = function(imageContent) {
    // Ext.MessageBox.wait('Loading......', '');
    var mask = new Ext.LoadMask(Ext.getCmp('content-panel').el, {
      msg : 'Loading...'
    });
    mask.show();
    var params = {};
    if (imageContent != undefined && imageContent.length > 0) {
      params['image_content'] = imageContent;
    }
    var panel = Ext.getCmp('image-list-panel');
    panel.removeAll();
    Ext.Ajax.request({
      url : root + '/image/list',
      method : 'get',
      params : params,
      success : function(resp) {
        mask.hide();
        var images = Ext.JSON.decode(resp.responseText);
        var items = [];
        for ( var i in images) {
          items.push(makeImageTemplate(images[i]));
        }
        panel.add(items);
        // Ext.MessageBox.hide();
      },
      failure : function() {
        mask.hide();
        // Ext.MessageBox.hide();
      }
    });
  };

  /**
   * Generate template for each image object.
   */
  var makeImageTemplate = function(image) {
    return {
      xtype : 'panel',
      id : 'image_' + image['id'],
      layout : 'column',
      width : 800,
      bodyStyle : {
        borderTop : 0,
        borderRight : 0,
        borderLeft : 0
      },
      items : [ {
        xtype : 'panel',
        border : false,
        columnWidth : 0.8,
        bodyPadding : 10,
        html : '<p>' + image['name'] + '</p><p>' + image['knowledge'] + '</p>'
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
          xtype : 'button',
          text : '打开素材',
          //image_url : image['url'],
          handler : function(btn) {
            window.open(image['url']);
            /*
            Ext.widget('window', {
              title : image['name'],
              layout : 'fit',
              width : 800,
              height : 600,
              resizable : true,
              items : {
                xtype : 'image',
                src : btn.image_url
              }
            }).show();
            */
          }
        } ]
      } ]
    };
  };

  var getFilterContent = function() {
    var content = Ext.getCmp('image-search').getValue();
    fetchImages(content);
  };

  var searchPanel = {
    xtype : 'panel',
    region : 'north',
    margin : '0 20 0 20',
    bodyPadding : '15 0 10 0',
    bodyStyle : {
      borderTop : 0,
      borderRight : 0,
      borderLeft : 0
    },
    layout : 'column',
    items : [ {
      xtype : 'textfield',
      id : 'image-search',
      name : 'imageSearch',
      width : 500,
      height : 30,
      emptyText : '搜索素材',
      listeners: {
        specialkey: function(f, e) {
          if(e.getKey() == e.ENTER) {
            getFilterContent();
          }
        }
      }
    }, {
      xtype : 'button',
      height : 30,
      margin : '0 0 0 10',
      padding : 8,
      text : '查询素材',
      handler : getFilterContent
    }, {
      xtype : 'panel',
      columnWidth : 1,
      border : false
    } ]
  };

  var filterForm = {
    xtype : 'form',
    id : 'image-type-form',
    region : 'west',
    width : 200,
    bodyStyle : {
      borderTop : 0,
      borderBottom : 0,
      borderLeft : 0
    },
    bodyPadding : '20 10 0 20',
    defaultType : 'checkboxfield',
    defaults : {
      listeners : {
        change : function(field, newValue, oldValue, opts) {
          /*
           * var form = Ext.getCmp('problem-type-form'); if (field.name ==
           * 'type_all' && newValue) {
           * form.queryById('type_concept').setValue(false);
           * form.queryById('type_blankfill').setValue(false);
           * form.queryById('type_choice').setValue(false);
           * form.queryById('type_question').setValue(false);
           * form.queryById('type_integrate').setValue(false); return; }
           * form.queryById('type_all').setValue(false); var values =
           * form.getForm().getValues(); var types = []; for ( var i in values) {
           * types.push(i); } if (types.length == 0) {
           * form.queryById('type_all').setValue(true); }
           */
        }
      }
    },
    items : [ {
      xtype : 'button',
      text : '过滤素材',
      margin : '0 0 10 0',
      handler : getFilterContent
    }, {
      boxLabel : '所有素材',
      id : 'type_all',
      name : 'type_all',
      checked : true
    } ]
  };

  var listForm = {
    xtype : 'panel',
    id : 'image-list-panel',
    region : 'center',
    border : 0,
    autoScroll : true,
    listeners : {
      afterrender : function(comp, opts) {
        fetchImages();
      }
    }
  };

  /**
   * Image Set Panel (Main)
   */
  var imagePanel = {
    xtype : 'panel',
    id : 'image-panel',
    layout : 'border',
    bodyBorder : false,
    items : [ searchPanel, filterForm, listForm ]
  };

  var contentPanel = Ext.getCmp("content-panel");
  contentPanel.removeAll();
  contentPanel.add(imagePanel);
});