(ns oseri.model
  (:require [oseri.view :refer [->Tile]]))

(def direction #{:N :NE :E :SE :S :SW :W :NW})

; (MEMO: (row,col))
; (0,0)(0,1)(0,2)
; (1,0)(1,1)(1,2)
; (2,0)(2,1)(2,2)
(defn successor [dir pos] (let [north #(update % :row dec)
                                east #(update % :col inc)
                                south #(update % :row inc)
                                west #(update % :col dec)]
                            ((case dir
                               :N north
                               :NE (comp north east)
                               :E east
                               :SE (comp south east)
                               :S south
                               :SW (comp south west)
                               :W west
                               :NW (comp north west)) pos)))

(defn wrapped? [p b] (let [min 0
                           max (count b)]
                       (and
                        (>= (:row p) min)
                        (< (:col p) max)
                        (< (:row p) max)
                        (>= (:col p) min))))

(defn get-tile [b p] (let [row (:row p) col (:col p)]
                       (-> b (nth row) (nth col))))

(defn correct-line-cord [d p b] (if (not (wrapped? p b))
                                  '()
                                  (cons p (correct-line-cord d (successor d p) b))))

(defn correct-line [d p b] (->> b
                                (correct-line-cord d p)
                                (map (partial get-tile b))))

(defn -free? [tile] (= :empty (:color tile)))

(defn correct-valid-tiles [line] (cond
                                   (empty? line) '()
                                   (-free? (first line)) '()
                                   :else (let [head (first line) body (rest line)]
                                           (cons head (correct-valid-tiles body)))))

(defn clasp-impl? [color line] (cond
                                 (empty? line) true
                                 (= (:color (first line)) :empty) true
                                 :else (let [head (first line) body (rest line)]
                                         (and
                                          (not (= color (:color head)))
                                          (clasp-impl? color body)))))

(defn clasp? [color line] (let [line' (correct-valid-tiles line) tail (last line') body (drop-last line')]
                            (and
                             (not (empty? line'))
                             (= color (:color tail))
                             (clasp-impl? color body))))

(defn pointable? [b p color] (and
                              (-free? (get-tile b p))
                              (true? (some #(->> b
                                                 (correct-line % p)
                                                 rest
                                                 (clasp? color))
                                           direction))))

(defn initial-operations [size]
  (let [base (dec (quot size 2))
        south-east {:row base :col base}
        operations (cons south-east (map #(successor % {:row base :col base}) '(:E :S :SE)))]
    (map-indexed #(cond
                    (or (= %1 0) (= %1 3)) (->Tile (:row %2) (:col %2) :black)
                    :else (->Tile (:row %2) (:col %2) :white))
                 operations)))

(defn operate [brd oprt]
  (if (pointable? brd oprt (:color oprt))
    ""
    "Can't point here"))
