risk-credit  project
1. api module --> risk-credit entry
   
2. credit-common  -->  some common component
   
3. credit-engine  --> rule engine module which load all rules and quotas and execute related rules
   
4. credit-jade  -->  risk-credit db module which is used to save event to db
   
5. credit-service --> facade service and adapter for rule engine 
    
6. credit-thirdparty --> 
   6.1 credit-tongdun
   6.2 credit-zhima
   6.3 credit-entry
   this module is used to extend the third party entry
   
7. risk-credit web  --> web application for restful api and start dubbo service


In feature this web application will be separated into 5 micro service applications 
which are risk-credit-gateway, risk-credit-engine, risk-credit-jade, risk-credit-thirdparty