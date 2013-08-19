Ext.Loader.setConfig({enabled: true});

Ext.onReady(function(){
  
  Ext.create('Ext.Viewport', {
    layout: 'border',
    items: [{
      xtype: 'box',
      id: 'courseware-header',
      region: 'north',
      html: "<h1>Course Sharing Platform</h1>",
      height: 50,
      bodyPadding: 10
    }, {
      xtype: 'panel',
      id: 'courseware-west',
      region: 'west',
      width: 250,
      margins: 10
    }, {
      xtype: 'panel',
      id: 'courseware-center',
      region: 'center',
      margins: 10
    }, {
      xtype: 'box',
      id: 'footer',
      region: 'south',
      height: 20,
      bodyPadding: 10
    }]
    
  });
 
});