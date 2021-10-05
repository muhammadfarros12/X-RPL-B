fun main(){
        var jam = 26
         when(jam){
             in 1..5 -> println("bayar parkir sebanyak \$${jam * 1.0}")
             in 6..23 -> println("bayar parkir sebanyak\$${((jam - 5.0) *0.5)+5}")
             else -> println("bayar parkir sebanyak \$${(jam %24) * 0.5 + (jam/24*23)}")
         }
     }
