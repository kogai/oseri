(ns oseri.model)

; (MEMO: (row,col))
; (0,0)(0,1)(0,2)
; (1,0)(1,1)(1,2)
; (2,0)(2,1)(2,2)
(defn successor [dir pos] (let [north (fn [p] (merge p {:row (- (get p :row) 1)}))
                                east (fn [p] (merge p {:col (+ (get p :col) 1)}))
                                south (fn [p] (merge p {:row (+ (get p :row) 1)}))
                                west (fn [p] (merge p {:col (- (get p :col) 1)}))]
                            ((case dir
                               (:N) north
                               (:NE) (comp north east)
                               (:E) east
                               (:SE) (comp south east)
                               (:S) south
                               (:SW) (comp south west)
                               (:W) west
                               (:NW) (comp north west)) pos)))

(defn wrapped? [p b] (let [min 0
                           max (count b)]
                       (every? identity [(>= (get p :row) min)
                                         (< (get p :col) max) (< (get p :row) max) (>= (get p :col) min)])))

; 関数合成を使ってリファクタリングできそう
(defn get-tile [p b] (let [row (get p :row) col (get p :col)]
    (-> b (nth col) (nth row))))

(defn correct-line [dir pos board] true)