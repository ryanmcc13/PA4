FILES=$(addprefix bin/, $(addsuffix .class, Main PositionalIndex PreProcessor QueryProcessor))

TEST=$(addprefix bin/, $(addsuffix .class, QueryProcessorTest))

FLAGS= -Xlint -Werror -d bin/

.PHONY: default makebin clean

default: $(FILES)
	@echo "compilation successful"

test: FLAGS:=$(FLAGS) -g
test: $(TEST) $(FILES)
	@echo "test compilation successful"

bin/%.class: src/%.java | makebin
	@echo "compiling $<"
	@javac $(FLAGS) -cp bin:src/ $<

bin/%.class: test/%.java | makebin
	@echo "compiling $<"
	@javac $(FLAGS) -cp ${CLASSPATH}:bin:src/:test/ $<

clean:
	@[ ! -d bin ] || echo "removing bin directory"
	@[ ! -d bin ] || rm -r bin

makebin:
	@[  -d bin ] || echo "making bin directory"
	@[  -d bin ] || mkdir bin
