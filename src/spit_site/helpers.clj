(ns spit-site.helpers
  (:use [clojure.java.io :only (file delete-file)]
        [clojure.string :only (join)]))

(defn relativize
  "Takes two files and returns the relative path between them"
  [base-file path-file]
  (let [uri-base (.toURI base-file)
        uri-path (.toURI path-file)
        relative-path (.getPath (.relativize uri-base uri-path))]
    (if (.equals (.getAbsolutePath path-file) relative-path)
      nil ; Was not relative to the base
      relative-path)))

(defn path-of
  "Extract the directory component(s) of a file path"
  [the-file]
  (let [path (.getPath the-file)
        name (.getName the-file)
        diff (- (count path) (count name))
        resolved-path (-> (take (- diff 1) path) (join))]
    (if (-> (count resolved-path) (= 0)) nil resolved-path)))

; File utils

(defn wipe-dir
  "Clean out a given directory by removing all contained
  files and subfolders"
  ([root] (wipe-dir root false))

  ([root dry-run]
    (let [file-list (reverse (rest (file-seq (file root))))]
      (doseq [del-file file-list]
        (println (str (if dry-run "NOT " "") "Deleting " (.toString del-file)))
        (if-not dry-run (delete-file del-file))))))

(defn filter-on-extension
  "Filtes the provided list of files on extension"
  [file-coll ext]
  (filter #(.endsWith (.getName %) (str "." ext)) file-coll))
