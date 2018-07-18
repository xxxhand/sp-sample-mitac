This is sample for MiTAC.

package: mvn clean package

exec: java -jar fat-build/sp-sample-mitac.jar

程式是依賴config檔運作，config是一個json檔，記錄了如資料庫連線、設備設定…等資訊。

本程式是以mLab提供的MongoDB服務作為儲存端，故啟用時須確認網路是可以連到mLab。
若是有其它已存在的mongoDB，則只要修改config裡的main_mongo.uri即可。


啟動成功會在console裡看到

UDP server is running... 

Http server up on 8080

各設備是以Type ID欄位做為設備id，上傳的資料若設備id不符，則不會儲存。

Formula: 每個設備裡的每個標籤都可設定計算公式，目前不知道詳細公式，僅以+、*…等簡易算法

API: 目前只有一支query by equipment id，url為

http get /api/equipments?eqp=135，eqp為設備的type id


