Library ieee;
Use ieee.std_logic_1164.ALL;
Use ieee.std_logic_arith.ALL;
Use ieee.std_logic_unsigned.ALL;
USE ieee.numeric_std.all;


ENTITY alu is
PORT(
	a : IN STD_logic_vector(31 downto 0);
	b : IN STD_logic_vector(31 downto 0);
	op: IN STD_logic_vector(2 downto 0);
	result: OUT STD_logic_vector(31 downto 0);
	Cout : OUT STD_logic;
	zero: OUT STD_logic);
END ALU;
ARCHITECTURE description OF alu is
begin
process(op)
begin
case op is 
		When "000"=> result <= a and b;
		When "001"=> result <= a or b;
		When "010"=> result <= a + b;
		When "110"=> result <= a - b;
		When "100"=> result <= a;
						 result(0) <= a(6);
						 result(1) <= a(0);
						 result(2) <= a(1);
						 result(3) <= a(2);
						 result(4) <= a(3);
						 result(5) <= a(4);
						 result(6) <= a(5);--rol
						 
		When "101"=> result <= a;
						 result(0) <= a(1);
						 result(1) <= a(2);
						 result(2) <= a(3);
						 result(3) <= a(4);
						 result(4) <= a(5);
						 result(5) <= a(6);
						 result(6) <= a(0);--ror
		when others => result <= "00000000000000000000000000000000";
end case;
end process;

END description;