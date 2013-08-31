Ext.onReady(function(){

  Ext.getCmp('content-panel').add({
    xtype: 'panel',
    layout: 'border',
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
  });

});
