Ext.Loader.setConfig({enabled: true});

Ext.require([
  'Ext.ux.layout.Center'
]);

Ext.onReady(function(){
  
  var header = {
    xtype: 'panel',
    region: 'north',
    layout: 'absolute',
    height: 50,
    bodyStyle: 'background: #F9FAF7',
    border: false,
    items: [{
      xtype: 'box',
      x: 0,
      y: 0,
      html: '<h3>Course Resource Sharing</h3>'
    }, {
      xtype: 'toolbar',
      x: 400,
      y: 15,
      baseCls: '',
      items: [
        '->', {
          text: 'Courseware',
          menu: {
            items: [{
              text: 'Review',
              handler: function(item) {
                alert(item);
              }
            }]
          }
        }, {
          text: 'Problem Set'
        }, 
        '-', {
          text: 'login'
        }
      ]
    }]
  };
  
  var content = {
    xtype: 'panel',
    layout: 'border',
    region: 'center',
    bodyBorder: false,
    bodyPadding: 10,
    items: [{
      xtype: 'panel',
      region: 'west',
      width: 250
    }, {
      xtype: 'panel',
      region: 'center',
      margins: '0 0 0 10'
    }]
  };
  
  var footer = {
    xtype: 'box',
    region: 'south',
    bodyStyle: 'background: #F9FAF7',
    border: false,
    align: 'center',
    html: '<p>Copyright Xian Yikun, 2013.08<p>'
  };
  
  Ext.create('Ext.container.Viewport', {
    style: {
      backgroundColor: '#F9FAF7'
    },
    layout: 'ux.center',
    items: [{
      xtype: 'panel',
      width: 1024,
      bodyStyle: 'background: #F9FAF7',
      border: false,
      layout: 'border',     
      items: [
        header,
        content,
        footer
      ]
    }]
    
  });
 
});