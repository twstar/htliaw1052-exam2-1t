# htliaw1052-exam2-1t

105-2 MI3C 補考一梯題
寫 class DW, 代表星期幾(days in a week). 
DW 中有 int 欄位 d, 0代表星期日, 1代表星期一, ... 6代表星期六.
DW 有 constructor, 含一個int參數, 用來設定 d 的值,
可以假設使用者的輸入都沒錯.
所有DW 的合法文字格式為(不含逗點) [0], [1], [2], [3], [4], [5], [6].

寫 class TestDW, 用來測試 DW 的 I/O.
TestDW中有 static 函數 test1(), test2(), test3(), test4()
test1() 依序造出三個物件並在DOS視窗印出, 資料為
        [2], [4], [0]
test2() 由鍵盤讀取3個 DW 物件並在DOS視窗印出
test3() 5月1日是星期一, 用迴圈造出5月1日到5月31日所對應的的DW物件, 
        然後將它們印到 data1.txt, 中間用空格分開.
test4() 由 data1.txt 讀取DW物件並在DOS視窗印出, 中間用但逗點及空白隔開.
        應印出 
[1], [2], [3], [4], [5], [6], [0], ... [1], [2], [3], 
(共印出31個DW物件, 由DOS視窗自己換行)

TestDW 的 main 切換 test1(), test2(), test3(), test4()

注意: ScanableI 已不用, 應改用 ScannableI
