// Based on jQuery ScrollPagination
// Original Lib refers to http://andersonferminiano.com/jqueryscrollpagination/

(function($) {

  $.fn.scrollPagination = function(options) {
    var opts = $.extend($.fn.scrollPagination.defaults, options);
    var target = opts.scrollTarget;
    if (target == null) {
      target = obj;
    }
    opts.scrollTarget = target;
    return this.each(function() {
      $.fn.scrollPagination.init($(this), opts);
    });
  };

  $.fn.stopScrollPagination = function() {
    return this.each(function() {
      $(this).attr('scrollPagination', 'disabled');
    });
  };

  $.fn.scrollPagination.loadContent = function(obj, opts) {
    var target = opts.scrollTarget;
    var mayLoadContent = $(target).scrollTop() + opts.heightOffset >= $(document).height() - $(target).height();
    if (mayLoadContent) {
      if (opts.beforeLoad != null) {
        opts.beforeLoad();
      }
      $(obj).children().attr('rel', 'loaded');
      $.ajax({
        url : opts.url,
        type : opts.method,
        data : opts.param,
        dataType: 'json',
        success : function(data) {
          //$(obj).append(data);
          //var objectsRendered = $(obj).children('[rel!=loaded]');
          if (opts.afterLoad != null) {
            opts.afterLoad(data);
          }
        }
      });
    }
  };

  $.fn.scrollPagination.init = function(obj, opts) {
    var target = opts.scrollTarget;
    $(obj).attr('scrollPagination', 'enabled');
    $(target).scroll(function(event) {
      if ($(obj).attr('scrollPagination') == 'enabled') {
        $.fn.scrollPagination.loadContent(obj, opts);
      } else {
        event.stopPropagation();
      }
    });
    $.fn.scrollPagination.loadContent(obj, opts);
  };

  $.fn.scrollPagination.defaults = {
    'url' : null,
    'type': 'GET',
    'param' : {},
    'beforeLoad' : null,
    'afterLoad' : null,
    'scrollTarget' : null,
    'heightOffset' : 0
  };
})(jQuery);