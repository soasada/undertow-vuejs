VUEAPPPID=$(~/jdk/bin/jps -l | grep 'undertow-vuejs-0.0.1.jar' | awk '{print $1}')
if [ -n "$VUEAPPPID" ]; then echo "Killing app with PID $VUEAPPPID" && kill -9 $VUEAPPPID; else echo 'App not running'; fi
