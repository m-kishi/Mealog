#
# jarファイルは VS Code からエクスポートしておく
# command + shift + p でパレットを開き，Java: Export Jar... から実行
#

all: jar icns

jar: ../Mealog.jar
	mv ../Mealog.jar Mealog.app/Contents/Java/Mealog.jar

icns: Mealog.icns
	mv Mealog.icns Mealog.app/Contents/Resources/Mealog.icns

Mealog.icns: Mealog.iconset/*.png
	iconutil -c icns Mealog.iconset
