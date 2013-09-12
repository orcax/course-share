var root = '/course-share';

Ext.Loader.setConfig({enabled: true});

Ext.require([
  'Ext.ux.layout.Center'
]);

Ext.onReady(function(){
  
  var header = {
    xtype: 'panel',
    region: 'north',
    layout: 'absolute',
    height: 60,
    bodyStyle: 'background: #F9FAF7',
    border: false,
    items: [{
      xtype: 'box',
      x: 0,
      y: 0,
      html: '<h3>计算机体系结构课程资源</h3>'
    }, {
      xtype: 'toolbar',
      x: 400,
      y: 15,
      baseCls: '',
      defaults: {
        margins: '0 5 0 5',
      },
      items: [
        '->', {
          text: '课程资源',
        }, {
          text: '课程题库'
        }
      ]
    }]
  };
  
  var content = {
    xtype: 'panel',
    id: 'content-panel',
    layout: 'fit',
    region: 'center',
    bodyStyle: 'background: #F9FAF7',
    border: false,
  };
  
  var footer = {
    xtype: 'box',
    region: 'south',
    bodyStyle: 'background: #F9FAF7',
    border: false,
    align: 'center',
    html: '<div id="footer"><p>Copyright Xian Yikun, 2013.08<p></div>'
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
