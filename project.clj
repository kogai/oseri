(defproject oseri "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
    [org.clojure/tools.nrepl "0.2.12"]
    [org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot oseri.core
  :target-path "target/%s"
  :plugins [
    [lein-cljfmt "0.5.6"]
    [cider/cider-nrepl "0.14.0"]]
  :profiles {:uberjar {:aot :all}})
