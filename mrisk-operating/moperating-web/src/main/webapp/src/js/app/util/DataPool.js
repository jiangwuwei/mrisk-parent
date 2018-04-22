var DataPool = function(){

    var pool = new Array();

    var enQueue  = function(obj) {
        pool.unshift(obj);
    };

    var deQueue  = function(){
        return pool.pop();
    };

    return {
        enQueue : enQueue,
        deQueue : deQueue
    }
};

