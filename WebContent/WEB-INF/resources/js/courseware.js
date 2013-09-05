Ext.onReady(function(){

  Ext.getCmp('content-panel').add({
    xtype: 'panel',
    id: 'courseware-panel',
    layout: 'border',
    bodyBorder: false,
    bodyPadding: 10,
    items: [{
      xtype: 'panel',
      region: 'north',
      height: 80
    }, {
      xtype: 'panel',
      region: 'center',
      margins: '10 0 0 0'
    }]
  });

});
