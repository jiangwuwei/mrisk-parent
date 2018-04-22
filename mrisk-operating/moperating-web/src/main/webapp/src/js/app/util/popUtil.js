/**confirm begin**/
/*
  use:
  var confirmA = new Confirm({
    'wrapper':xxx,
    'trigger':xxx,
    'confirmHandel':function(){
        //
    }
  })
 */
var Confirm = function(config){
    this.$wrapper = config.wrapper;
    this.confirmHandel=config.confirmHandel;
    this.$mask = $("#lean_overlay");
    this.$trigger = config.trigger;
}
Confirm.prototype.show = function(){
    this.$wrapper.show();
    this.$mask.show();
}
Confirm.prototype.hide = function(){
    this.$wrapper.hide();
    this.$mask.hide();
}
Confirm.prototype.init = function(){
    var self = this;
    var theBtn;
    function showTrigger(){
       theBtn = $(this);
      self.show();
    }
    if(typeof this.$trigger.length !== "undefined"){
      //jquery元素
      this.$trigger.on("click", showTrigger);
    }else{
      //'trigger':{'parent':$(""), 'selector':'.update_btn'}
      this.$trigger.parent.on("click", this.$trigger.selector, showTrigger);
    }
    this.$wrapper.find(".confirm_btn").on("click", function(){
      self.confirmHandel(theBtn);
      self.hide();
    });
    this.$wrapper.find(".cancel_btn").on("click", function(){
      self.hide();
    });
}
/**confirm end**/

/**pop begin**/
/*
  use:
  var popA = new Pop({
    'wrapper':xxx,
    'trigger':xxx,
    'beforeOpen':function(){
        //
    }
    'confirmHandel':function(){
        //
    }
  })
 */
var Pop = function(config){
    this.$wrapper = config.wrapper;
    this.$trigger = config.trigger;
    this.$close_btn = this.$wrapper.find(".pop_close");
    this.$submit_btn = config.submitBtn||this.$wrapper.find(".submit_btn");
    this.beforeOpen = config.beforeOpen||null;
    this.submitHandel = config.submitHandel||null;
}
Pop.prototype.show = function(){
    this.$wrapper.show();
}
Pop.prototype.hide = function(){
    this.$wrapper.hide();
}
Pop.prototype.init = function(){
    var self = this;
    function showTrigger(){
      // self.beforeOpen&&self.beforeOpen(self.$wrapper);
      self.beforeOpen&&self.beforeOpen(self.$wrapper, $(this));
      self.show();
    }
    if(typeof this.$trigger.length !== "undefined"){
      //jquery元素
      this.$trigger.on("click", showTrigger);
    }else{
      //'trigger':{'parent':$(""), 'selector':'.update_btn'}
      this.$trigger.parent.on("click", this.$trigger.selector, showTrigger);
    }
    this.$close_btn.on("click", function(){
      self.hide();
    })
    this.$submit_btn.on("click", function(){
      self.submitHandel&&self.submitHandel(self.$wrapper);
      // self.hide();
    })
}
/**pop end**/
/*
  use:
  var tip = new globalTip();
  tip.success("ok");
  tip.error("error");
 */
 function globalTip(config){
      this.timeout = (config&&config.timeout)||2000;
      this.hasInit = false;
      this.$tipEle = $("<p style='padding:3px 20px;font-size:14px;position:fixed;top:82px;left:50%;margin-left:40px;color:white;border-radius:0 0 4px 4px;box-shadow:0px 3px 3px #ccc;'></p>");
      this.initDom = function(){
          $('body').append(this.$tipEle);
          this.hasInit = true;
      }
      this.showTip = function(type, msg){
          if(!this.hasInit){
              this.initDom();
          }
          var bgcolor = "#278eda";
          if(type=="error"){
              bgcolor = "#DA4027";
          }
          this.$tipEle.text(msg).css("background-color", bgcolor).fadeIn().delay(this.timeout).fadeOut(); 
      }
  }

  globalTip.prototype.success = function(msg){
      this.showTip("success", msg);
  }
  globalTip.prototype.error = function(msg){
      this.showTip("error", msg);
  }


