# cache-aside-pattern
## 究竟应该先操作缓存，还是数据库？
  先操作db:  
  先操作db情景1:  
  第一步:update db失败,直接return 5XX  
  第二步:update cache不执行  
  db和cache都是老数据,不会出现数据不一致。  

  先操作db情景2:  
  第一步:update db成功  
  第二步:update cache失败  
  db新数据,cache老数据,数据不一致,业务无法接受。 


  先操作cache:  
  先操作cache情景1:  
  第一步:update cache失败,直接return 5XX  
  第二步:update db不执行  
  db和cache都是老数据,不会出现数据不一致。  

  先操作cache情景2:  
  第一步:update cache(set/delete)成功  
  第二步:update db失败  
  update cache一般有两种手段,set或者delete,这样先操作cache会演变出两种场景：  
  场景2.1(通过set更新缓存):  
  这样cache新数据,db老数据，数据不一致,业务无法接受。  
  场景2.2(通过delete清除缓存):  
  这样会导致缓存没有数据，数据库还是老数据，数据没有不一致，对业务无影响。只是下一次读取时，会多一次cache miss。  

  综上所述:db cache双写时,应该先操作cache,并且更新原则用淘汰(delete)而不是修改(set)。  
