(defproject spit-site "0.1.0-SNAPSHOT"
  :description "
    Simple site generator based on the equation: templates + data = site.

    Renders templates using Mustache, and uses JSON as data format."
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/tools.cli "0.2.2"]
                 [org.clojure/data.json "0.2.1"]
                 [de.ubercode.clostache/clostache "1.3.1"]]
  :main spit-site.core)
