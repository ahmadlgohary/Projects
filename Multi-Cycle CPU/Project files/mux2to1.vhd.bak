library ieee;
use ieee.std_logic_1164.all; 

entity mux2to1 is
	port (
		s : in std_logic;
		w0, w1 : in std_logic_vector (31 downto 0); 
		f : out std_logic_vector (31 downto 0)
	); 
end mux2to1;

architecture behaviour of mux2to1 is 
begin
  process (w0, w1, s)
  begin
    if s = '0' then
      f <= w0;
    else
      f <= w1;
    end if;
  end process;
end behaviour;