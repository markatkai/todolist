/**
 * Generic button.
 * @param param0 
 * @returns 
 */
export function Button({ title, buttonOnClick, className }: 
  { 
    title?: string; 
    buttonOnClick: () => void, 
    className?: string 
  }) {
  return (
    <button onClick={buttonOnClick} className={className}>
        {title}
    </button>
  );
}
