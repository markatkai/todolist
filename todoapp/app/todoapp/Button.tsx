/**
 * Generic button.
 * @param param0 
 * @returns 
 */
export function Button({ title, buttonOnClick }: { title: string; buttonOnClick: () => void }) {
  return (
    <button onClick={buttonOnClick}>
        {title}
    </button>
  );
}
