var merge = require('easy-pdf-merge');

merge(['1.pdf','2.pdf'],'Merged.pdf',function(err){

        if(err)
        return console.log(err);

        console.log('Successfully merged!');

});
